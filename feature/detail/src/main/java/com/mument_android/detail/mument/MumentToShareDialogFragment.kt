package com.mument_android.detail.mument

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AnimationUtils
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
import com.mument_android.detail.R
import com.mument_android.detail.databinding.FragmentMumentToShareDialogBinding
import com.mument_android.detail.viewmodels.MumentDetailViewModel
import com.mument_android.domain.entity.detail.MumentEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MumentToShareDialogFragment(
    val captureCallback: (File, Uri) -> Unit
): DialogFragment() {
    private var binding by AutoClearedValue<FragmentMumentToShareDialogBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()
    @Inject lateinit var mediaUtils: MediaUtils

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(com.mument_android.core_dependent.R.style.DialogExposeAnimation)
    }

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

        setUpMumentTagList()
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
            viewModel.emitEvent(MumentDetailContract.MumentDetailEvent.UpdateMumentToShareInstagram(mument))
        }
    }

    private fun renderView() {
        collectFlowWhenStarted(viewModel.viewState) { state ->
            checkImagesRendered(state)
            state.mument?.let { mument ->
                with(binding) {
                    tvWriterName.text = state.mument.writerInfo.name
                    tvMument.text = mument.content
                    tvMusicName.text = mument.musicInfo.name
                    tvDate.text = mument.createdDate
                    (rvTags.adapter as MumentTagListAdapter).submitList(mument.combineTags())
                    ivProfileImage.loadImage(mument.writerInfo.profileImage?: "") {
                        viewModel?.updateRenderedProfileImage(true)
                    }
                    ivAlbumCover.loadImage(mument.musicInfo.thumbnail) {
                        viewModel?.updateRenderedAlbumCover(true)
                    }
                }
            }
        }
    }

    private fun setUpMumentTagList() {
        binding.rvTags.run {
            addItemDecoration(RecyclerviewItemDivider(0, 4.dpToPx(requireContext()), RecyclerviewItemDivider.IS_VERTICAL))
            layoutManager = FlexboxLayoutManager(context).apply {
                justifyContent = JustifyContent.CENTER
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            adapter = MumentTagListAdapter()
            binding.rvTags.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewModel.updateRenderedTags(true)
                    binding.rvTags.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    private fun checkImagesRendered(state: MumentDetailContract.MumentDetailViewState) {
        with(state) {
            if (renderedProfileImage && renderedTags && renderdAlbumCover) {
                mediaUtils.getBitmapUri(binding.cslRoot, FILE_NAME).let { fileInfo ->
                    fileInfo?.let { dismissWithDelay(it.first, it.second) }
                        ?: dismiss()
                }
            }
        }
    }

    private fun dismissWithDelay(file: File, uri: Uri) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(600)
            withContext(Dispatchers.Main) { captureCallback(file, uri) }
            dismiss()
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
        private const val FILE_NAME = "MumentShareImage"
        const val KEY_PASS_MUMENT = "KEY_PASS_MUMENT"
    }
}