package com.mument_android.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.mument_android.core.model.TagEntity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.MediaUtils
import com.mument_android.core_dependent.util.RecyclerviewItemDivider
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.core_dependent.util.ViewUtils.getDeviceSize
import com.mument_android.detail.databinding.FragmentMumentToShareDialogBinding
import com.mument_android.detail.mument.MumentDetailContract
import com.mument_android.detail.viewmodels.MumentDetailViewModel
import com.mument_android.domain.entity.detail.MumentEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MumentToShareDialogFragment(
    val captureCallback: (Uri) -> Unit
): DialogFragment() {
    private var binding by AutoClearedValue<FragmentMumentToShareDialogBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()
    @Inject lateinit var mediaUtils: MediaUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentToShareDialogBinding.inflate(inflater, container, false).let {
        binding = it
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        it.root
    }

    override fun onResume() {
        super.onResume()
        setDialogSize()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        getMumentArgs()
        renderView()
    }

    private fun setDialogSize() {
        val deviceSize = requireActivity().getDeviceSize()
        val deviceWidth = deviceSize[0]
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth * 0.75).toInt()
        params?.height = (deviceWidth * 0.75 * 1.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun getMumentArgs() {
        arguments?.getString(KEY_PASS_MUMENT)?.let {
            val mument = Gson().fromJson(it, MumentEntity::class.java)
            viewModel.emitEvent(MumentDetailContract.MumentDetailEvent.RenderMumentToShare(mument))
            renderMumentTags(mument.combineTags())
            checkImagesRendered(mument)
        }
    }

    private fun renderView() {
        collectFlowWhenStarted(viewModel.viewState) {
            it.mument?.let { mument ->
                with(binding) {
                    tvWriterName.text = it.mument.writerInfo.name
                    tvMument.text = mument.content
                    tvMusicName.text = mument.musicInfo.name
                    tvDate.text = mument.createdDate
                }
            }
        }
    }

    private fun renderMumentTags(mumentTags: List<TagEntity>) {
        binding.rvTags.run {
            addItemDecoration(RecyclerviewItemDivider(0, 4.dpToPx(requireContext()), RecyclerviewItemDivider.IS_VERTICAL))
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.CENTER
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            adapter = MumentTagListAdapter()
            (adapter as MumentTagListAdapter).submitList(mumentTags)
            binding.rvTags.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewModel.chagneIsRenderTags(true)
                    binding.rvTags.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    private fun checkImagesRendered(mument: MumentEntity) {
        binding.ivProfileImage.loadImage(mument.writerInfo.profileImage?: "") {
            viewModel.changeIsRenderProfile(true)
        }

        binding.ivAlbumCover.loadImage(mument.musicInfo.thumbnail) {
            viewModel.changeIsRenderAlbum(true)
        }

        collectFlowWhenStarted(viewModel.isEveryImageRender) { isEveryImageRender ->
            if (isEveryImageRender) {
                mediaUtils.getBitmapUri(binding.cslRoot, "MumentShareImage")?.let {
                    captureCallback(it)
                    dismiss()
                }
            }
        }
    }

    private inline fun ImageView.loadImage(url: String, crossinline finishRenderCallback:() -> Unit) {
        load(url) {
            allowHardware(false)
            listener(object: ImageRequest.Listener {
                override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                    super.onSuccess(request, metadata)
                    finishRenderCallback()
                }
            })
        }
    }

    companion object {
        const val KEY_PASS_MUMENT = "KEY_PASS_MUMENT"
    }
}