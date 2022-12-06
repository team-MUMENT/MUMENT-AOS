package com.mument_android.detail.mument

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.navigation.EditMumentNavigatorProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
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
import com.mument_android.detail.databinding.FragmentMumentDetailBinding
import com.mument_android.detail.mument.MumentDetailContract.MumentDetailEvent
import com.mument_android.detail.mument.MumentDetailContract.MumentDetailSideEffect
import com.mument_android.detail.mument.MumentToShareDialogFragment.Companion.KEY_PASS_MUMENT
import com.mument_android.detail.viewmodels.MumentDetailViewModel
import com.mument_android.domain.entity.detail.MumentEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MumentDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMumentDetailBinding>()
    private val viewModel: MumentDetailViewModel by viewModels()
    @Inject lateinit var editMumentNavigatorProvider: EditMumentNavigatorProvider
    @Inject lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider
    @Inject lateinit var musicDetailNavigatorProvider: MusicDetailNavigatorProvider

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

        receiveMumentID()
        updateMumentDetail()
        popBackStack()
        receiveEffect()
        setMumentTags()
        changeLikeStatus()
        showEditBottomSheet()
        goToMusicDetail()
        goToHistory()
        shareMumentOnInstagram()
    }

    private fun receiveMumentID() {
        arguments?.getString(MUMENT_ID)?.let {
            viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(it))
        }
    }

    private fun popBackStack() {
        binding.ivBackButton.setOnClickListener {
            viewModel.emitEvent(MumentDetailEvent.OnClickBackIcon)
        }
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
            (binding.rvMumentTags.adapter as MumentTagListAdapter).submitList(it.mument?.combineTags())
            binding.cslRoot.run { if (it.onNetwork) showProgress() else removeProgress() }
            if (it.hasError) requireContext().showToast("데이터를 불러올 수 없습니다.")
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
            viewModel.emitEvent(event)
        }
    }

    private fun showEditBottomSheet() {
        binding.ivKebab.click {
            EditMumentDialogFragment(object : EditMumentDialogFragment.EditListener {
                override fun edit() {
                    viewModel.viewState.value.mument?.content?.let { mument ->
                        viewModel.emitEvent(MumentDetailEvent.OnClickEditMument(mument))
                    }
                }

                override fun delete() {
                    MumentDialogBuilder()
                        .setHeader("삭제하시겠어요?")
                        .setAllowListener { viewModel.emitEvent(MumentDetailEvent.OnClickDeleteMument) }
                        .build()
                        .show(childFragmentManager, this@MumentDetailFragment.tag)
                }
            }).show(childFragmentManager, this.tag)
        }
    }

    private fun goToMusicDetail() {
        binding.viewAlbumClickArea.setOnClickListener {
            viewModel.viewState.value.mument?.musicInfo?.id?.let { musicId ->
                viewModel.emitEvent(MumentDetailEvent.OnClickAlum(musicId))
            }
        }
    }

    private fun goToHistory() {
        binding.tvGoToHistory.setOnClickListener {
            viewModel.viewState.value.mument?.musicInfo?.id?.let { musicId ->
                viewModel.emitEvent(MumentDetailEvent.OnClickHistory(musicId))
            }
        }
    }

    private fun shareMumentOnInstagram() {
        binding.ivShare.setOnClickListener {
            viewModel.viewState.value.mument?.let {
                viewModel.emitEvent(MumentDetailEvent.OnClickShareMument(it))
            }
        }
    }

    private fun openShareMumentDialog(mument: MumentEntity) {
        MumentToShareDialogFragment { uri ->
            viewModel.emitEvent(MumentDetailEvent.OnDismissShareMumentDialog(uri))
        }.apply {
            Bundle().apply {
                val mumentJson = Gson().toJson(mument)
                putString(KEY_PASS_MUMENT, mumentJson)
                arguments = this
            }
        }.show(childFragmentManager, "")
    }

    private fun navToInstagram(uri: Uri) {
        Intent(SHARE_INSTAGRAM_STORY_ID).apply {
            putExtra(APP_ID_KEY, requireContext().packageName)
            type = TYPE_IMAGE
            putExtra(STICKER_ASSET_KEY, uri)
            requireContext().grantUriPermission(INSTAGRAM_ID, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                startActivity(this)
            } catch (e: ActivityNotFoundException) {
                requireContext().showToast("Instagram이 설치되어 있지 않습니다.")
            }
        }
    }


    private fun receiveEffect() {
        collectFlowWhenStarted(viewModel.effect) { effect ->
            when(effect) {
                MumentDetailSideEffect.PopBackStack -> findNavController().popBackStack()
                MumentDetailSideEffect.SuccessMumentDeletion -> findNavController().popBackStack()
                is MumentDetailSideEffect.EditMument -> { /** Todo: Navigate To Edit Mument **/ }
                is MumentDetailSideEffect.NavToMusicDetail -> { /** Todo: Navigate To MusicDetail **/ }
                is MumentDetailSideEffect.NavToMumentHistory -> { /** Todo: Navigate To MumentHistory **/ }
                is MumentDetailSideEffect.Toast -> requireContext().showToast(effect.message)
                is MumentDetailSideEffect.OpenShareMumentDialog -> {openShareMumentDialog(effect.mument) }
                is MumentDetailSideEffect.NavToInstagram -> { navToInstagram(effect.imageUri) }
            }
        }
    }

    companion object {
        private const val INSTAGRAM_ID = "com.instagram.android"
        private const val SHARE_INSTAGRAM_STORY_ID = "com.instagram.share.ADD_TO_STORY"
        private const val APP_ID_KEY = "source_application"
        private const val STICKER_ASSET_KEY = "interactive_asset_uri"
        const val MUMENT_ID = "MUMENT_ID"
        private const val TYPE_IMAGE = "image/*"
    }
}