package com.mument_android.detail.mument.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.navigation.EditMumentNavigatorProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
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
import com.mument_android.detail.R
import com.mument_android.detail.databinding.FragmentMumentDetailBinding
import com.mument_android.detail.mument.contract.MumentDetailContract.MumentDetailEvent
import com.mument_android.detail.mument.contract.MumentDetailContract.MumentDetailSideEffect
import com.mument_android.detail.mument.fragment.MumentToShareDialogFragment.Companion.KEY_PASS_MUMENT
import com.mument_android.detail.mument.fragment.MumentToShareDialogFragment.Companion.KEY_PASS_MUSIC
import com.mument_android.detail.mument.viewmodel.MumentDetailViewModel
import com.mument_android.detail.report.SelectReportTypeDialogFragment
import com.mument_android.detail.report.SelectReportTypeDialogFragment.Companion.SELECT_BLOCK_USER
import com.mument_android.detail.report.SelectReportTypeDialogFragment.Companion.SELECT_REPORT_MUMENT
import com.mument_android.domain.entity.detail.MumentEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

        receiveMumentInfo()
        updateMumentDetail()
        popBackStack()
        receiveEffect()
        setMumentTags()
        changeLikeStatus()
        clickOptionButton()
        goToMusicDetail()
        goToHistory()
        shareMumentOnInstagram()

    }

    private fun receiveMumentInfo() {
        arguments?.getString(MUMENT_ID)?.let {
            viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(it))
        }
        arguments?.getString(MUSIC_INFO)?.let {
            val musicInfo = Gson().fromJson(it, MusicInfoEntity::class.java)
            viewModel.emitEvent(MumentDetailEvent.ReceiveMusicInfo(musicInfo))
        }
    }

    private fun popBackStack() {
        binding.ivBackButton.setOnClickListener {
            viewModel.emitEvent(MumentDetailEvent.OnClickBackButton)
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
            if (it.hasError) requireContext().showToast(resources.getString(R.string.cannot_load_data))
            showMumentHistory(it.hasWrittenMument)
        }
    }

    private fun showMumentHistory(show: Boolean) {
        binding.tvGoToHistory.run {
            if (show && visibility == View.GONE) applyVisibilityAnimation(
                isUpward = true,
                reveal = true,
                durationTime = 700,
                delay = 150
            )
        }
    }

    private fun changeLikeStatus() {
        binding.cbHeart.setOnClickListener {
            val event = if (binding.cbHeart.isChecked) MumentDetailEvent.OnClickLikeMument else MumentDetailEvent.OnClickUnLikeMument
            viewModel.emitEvent(event)
        }
    }

    private fun clickOptionButton() {
        binding.ivKebab.setOnClickListener {
            viewModel.emitEvent(MumentDetailEvent.OnClickOptionButton)
        }
    }

    private fun showEditOrDeleteMumentDialog() {
        SelectMumentEditTypeDialogFragment()
            .setEditListener(object : SelectMumentEditTypeDialogFragment.EditListener {
                override fun edit() {
                    viewModel.viewState.value.mument?.content?.let { mument ->
                        viewModel.emitEvent(MumentDetailEvent.SelectMumentEditType(mument))
                    }
                }

                override fun delete() {
                    viewModel.emitEvent(MumentDetailEvent.SelectMumentDeletionType)
                }
            }).show(childFragmentManager, this.tag)
    }

    private fun showMumentDeletionDialog() {
        MumentDialogBuilder()
            .setHeader("삭제하시겠어요?")
            .setAllowListener { viewModel.emitEvent(MumentDetailEvent.OnClickDeleteMument) }
            .build()
            .show(childFragmentManager, this@MumentDetailFragment.tag)
    }

    private fun showBlockOrReportBottomSheet() {
        SelectReportTypeDialogFragment()
            .attachSelectListener { type ->
                when(type) {
                    SELECT_REPORT_MUMENT -> viewModel.emitEvent(MumentDetailEvent.SelectReportMumentType)
                    SELECT_BLOCK_USER -> viewModel.emitEvent(MumentDetailEvent.SelectBlockUserType)
                }
            }.show(childFragmentManager, null)
    }

    private fun showBlockUserDialog() {
        with(requireContext()) {
            MumentDialogBuilder()
                .setHeader(getString(R.string.block_user_header))
                .setBody(getString(R.string.block_user_body))
                .setAllowListener(getString(R.string.notify_block)) {
                    viewModel.emitEvent(MumentDetailEvent.OnClickBlockUser)
                }.setCancelListener(getString(com.mument_android.core_dependent.R.string.cancel)) {

                }.build()
                .show(childFragmentManager, null)
        }
    }

    private fun goToMusicDetail() {
        binding.viewAlbumClickArea.setOnClickListener {
            viewModel.viewState.value.musicInfo?.id?.let { musicId ->
                viewModel.emitEvent(MumentDetailEvent.OnClickAlum(musicId))
            }
        }
    }

    private fun goToHistory() {
        binding.tvGoToHistory.setOnClickListener {
            viewModel.viewState.value.musicInfo?.id?.let { musicId ->
                viewModel.emitEvent(MumentDetailEvent.OnClickHistory(musicId))
            }
        }
    }

    private fun shareMumentOnInstagram() {
        binding.ivShare.setOnClickListener {
            val (mument, music) =
                if (checkIfAppInstalled(INSTAGRAM_PACKAGE_NAME)) viewModel.viewState.value.mument to viewModel.viewState.value.musicInfo else null to null
            viewModel.emitEvent(MumentDetailEvent.OnClickShareMument(mument, music))
        }
    }

    private fun openShareMumentDialog(mument: MumentEntity, musicInfoEntity: MusicInfoEntity) {
        MumentToShareDialogFragment { file, uri ->
            viewModel.emitEvent(MumentDetailEvent.OnDismissShareMumentDialog(file, uri))
        }.apply {
            Bundle().apply {
                val mumentJson = Gson().toJson(mument)
                val musicJson = Gson().toJson(musicInfoEntity)
                putString(KEY_PASS_MUMENT, mumentJson)
                putString(KEY_PASS_MUSIC, musicJson)
                arguments = this
            }
        }.show(childFragmentManager, "")
    }

    private fun navToInstagram(uri: Uri) {
        Intent(SHARE_INSTAGRAM_STORY_ID).apply {
            type = TYPE_IMAGE
            putExtra(APP_ID_KEY, requireContext().packageName)
            putExtra(STICKER_ASSET_KEY, uri)
            putExtra(STORY_TOP_BACKGROUND_COLOR_KEY, STORY_BACKGROUND_COLOR_VALUE)
            putExtra(STORY_BOTTOM_BACKGROUND_COLOR_KEY, STORY_BACKGROUND_COLOR_VALUE)
            requireContext().grantUriPermission(
                INSTAGRAM_PACKAGE_NAME,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            kotlin.runCatching {
                navInstagramLauncher.launch(this)
            }
        }
    }

    private fun checkIfAppInstalled(name: String): Boolean {
        return kotlin.runCatching {
            requireContext().packageManager.getPackageInfo(name, 0)
        }.isSuccess
    }

    private val navInstagramLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.emitEvent(MumentDetailEvent.EntryFromInstagram)
        }

    private fun receiveEffect() {
        collectFlowWhenStarted(viewModel.effect) { effect ->
            when (effect) {
                MumentDetailSideEffect.PopBackStack -> findNavController().popBackStack()
                MumentDetailSideEffect.SuccessMumentDeletion -> findNavController().popBackStack()

                MumentDetailSideEffect.OpenEditOrDeleteMumentDialog -> { showEditOrDeleteMumentDialog() }
                is MumentDetailSideEffect.NavToEditMument -> { /** Todo: Navigate To Edit Mument **/ }
                MumentDetailSideEffect.OpenDeleteMumentDialog -> showMumentDeletionDialog()

                MumentDetailSideEffect.OpenBlockOrReportBottomSheet -> { showBlockOrReportBottomSheet() }
                MumentDetailSideEffect.NavToReportMument -> { /** Todo: Navigate To Report Mument **/ }
                MumentDetailSideEffect.OpenBlockUserDialog -> showBlockUserDialog()

                is MumentDetailSideEffect.NavToMusicDetail -> { musicDetailNavigatorProvider.fromMumentDetailToMusicDetail(effect.musicId) }
                is MumentDetailSideEffect.NavToMumentHistory -> { /** Todo: Navigate To MumentHistory **/ }
                is MumentDetailSideEffect.Toast -> requireContext().showToast(resources.getString(effect.message))
                is MumentDetailSideEffect.OpenShareMumentDialog -> { openShareMumentDialog(effect.mument,  effect.musicInfo) }
                is MumentDetailSideEffect.NavToInstagram -> { navToInstagram(effect.imageUri) }
            }
        }
    }

    companion object {
        private const val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"
        private const val SHARE_INSTAGRAM_STORY_ID = "com.instagram.share.ADD_TO_STORY"
        private const val APP_ID_KEY = "source_application"
        private const val STICKER_ASSET_KEY = "interactive_asset_uri"
        private const val STORY_TOP_BACKGROUND_COLOR_KEY = "top_background_color"
        private const val STORY_BOTTOM_BACKGROUND_COLOR_KEY = "bottom_background_color"
        private const val STORY_BACKGROUND_COLOR_VALUE = "#989898"
        const val INSTA_STORY_BACKGROUND_COLOR = "#989898"
        const val MUMENT_ID = "MUMENT_ID"
        const val MUSIC_INFO = "MUSIC_INFO"
        private const val TYPE_IMAGE = "image/*"
    }
}