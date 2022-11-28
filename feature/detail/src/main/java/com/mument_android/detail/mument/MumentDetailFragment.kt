package com.mument_android.detail.mument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.navigation.EditMumentNavigatorProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.RecyclerviewItemDivider
import com.mument_android.core_dependent.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.mument_android.core_dependent.util.ViewUtils.applyVisibilityAnimation
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.core_dependent.util.removeProgress
import com.mument_android.core_dependent.util.showProgress
import com.mument_android.detail.BuildConfig
import com.mument_android.detail.databinding.FragmentMumentDetailBinding
import com.mument_android.detail.viewmodels.MumentDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.mument_android.detail.mument.MumentDetailContract.*

@AndroidEntryPoint
class MumentDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentDetailBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()

    @Inject
    lateinit var editMumentNavigatorProvider: EditMumentNavigatorProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

    @Inject
    lateinit var musicDetailNavigatorProvider: MusicDetailNavigatorProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mumentDetailViewModel = viewModel
        binding.ivBackButton.setOnClickListener { findNavController().popBackStack() }

        arguments?.getString(MUMENT_ID)?.let {
            viewModel.updateRequestMumentId(it)
        }

        updateMumentDetail()
        receiveEffect()
        setMumentTags()
        changeLikeStatus()
        showEditBottomSheet()
        goToMusicDetail()
        goToHistory()
    }

    private fun setMumentTags() {
        with(binding) {
            rvMumentTags.adapter = MumentTagListAdapter()
            rvMumentTags.layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
            rvMumentTags.addItemDecoration(RecyclerviewItemDivider(7, 12, IS_GRIDLAYOUT))
        }
    }

    private fun updateMumentDetail() {
        collectFlowWhenStarted(viewModel.viewState) {
            binding.ivKebab.visibility = if (it.isWriter) View.VISIBLE else View.GONE
            (binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(it.mument?.combineTags())
            binding.constraintlayoutRoot.run { if (it.onNetwork) showProgress() else removeProgress() }
            if (it.hasError) Toast.makeText(requireContext(), "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            showMumentHistory(it.hasWrittenMument)
        }
    }

    private fun showMumentHistory(show: Boolean) {
        binding.tvGoToHistory.run {
            if(show && visibility == View.GONE) applyVisibilityAnimation(isUpward = true, reveal = true, durationTime = 700, delay = 150)
        }
    }

    private fun changeLikeStatus() {
        binding.cbHeart.click {
            val event = if (binding.cbHeart.isChecked) MumentDetailEvent.OnClickLikeMument else MumentDetailEvent.OnClickUnLikeMument
            viewModel.emitUserEvent(event)
        }
    }

    private fun showEditBottomSheet() {
        binding.ivKebab.click {
            EditMumentDialogFragment(object : EditMumentDialogFragment.EditListener {
                override fun edit() {
                    viewModel.viewState.value.mument?.content?.let { mument ->
                        viewModel.emitUserEvent(MumentDetailEvent.OnClickEditMument(mument))
                    }
                }

                override fun delete() {
                    MumentDialogBuilder()
                        .setHeader("삭제하시겠어요?")
                        .setAllowListener { viewModel.emitUserEvent(MumentDetailEvent.OnClickDeleteMument) }
                        .build()
                        .show(childFragmentManager, this@MumentDetailFragment.tag)
                }
            }).show(childFragmentManager, this.tag)
        }
    }

    private fun goToMusicDetail() {
        binding.viewAlbumClickArea.setOnClickListener {
            viewModel.viewState.value.mument?.musicInfo?.id?.let { musicId ->
                viewModel.emitUserEvent(MumentDetailEvent.OnClickAlum(musicId))
            }
        }
    }

    private fun goToHistory() {
        binding.tvGoToHistory.setOnClickListener {
            viewModel.viewState.value.mument?.musicInfo?.id?.let { musicId ->
                viewModel.emitUserEvent(MumentDetailEvent.OnClickHistory(musicId))
            }
        }
    }

    private fun receiveEffect() {
        collectFlowWhenStarted(viewModel.effect) { effect ->
            when(effect) {
                MumentDetailSideEffect.FailMumentDeletion -> { requireContext().showToast("뮤멘트를 삭제하지 못했습니다.") }
                MumentDetailSideEffect.SuccessMumentDeletion -> findNavController().popBackStack()
                MumentDetailSideEffect.PopBackStack -> findNavController().popBackStack()
                is MumentDetailSideEffect.EditMument -> { /** Todo: (Navigate To Edit Mument) **/ }
                is MumentDetailSideEffect.NavToMusicDetail -> { /** Todo: Navigate To MusicDetail**/ }
                is MumentDetailSideEffect.NavToMumentHistory -> TODO()
            }
        }
    }

    companion object {
        const val MUMENT_ID = "MUMENT_ID"
    }
}