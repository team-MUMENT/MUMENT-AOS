package com.mument_android.detail.mument.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.navigation.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
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
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.detail.mument.contract.MumentDetailContract.MumentDetailEvent
import com.mument_android.detail.mument.contract.MumentDetailContract.MumentDetailSideEffect
import com.mument_android.detail.mument.fragment.MumentToShareDialogFragment.Companion.KEY_PASS_MUMENT
import com.mument_android.detail.mument.fragment.MumentToShareDialogFragment.Companion.KEY_PASS_MUSIC
import com.mument_android.detail.mument.viewmodel.MumentDetailViewModel
import com.mument_android.detail.report.SelectReportTypeDialogFragment
import com.mument_android.detail.report.SelectReportTypeDialogFragment.Companion.SELECT_BLOCK_USER
import com.mument_android.detail.report.SelectReportTypeDialogFragment.Companion.SELECT_REPORT_MUMENT
import com.mument_android.domain.entity.detail.MumentEntity
import com.mument_android.domain.entity.home.RecentSearchData
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.record.MumentModifyEntity
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

    @Inject
    lateinit var likeUsersNavigatorProvider: LikeUsersNavigatorProvider

    @Inject
    lateinit var mumentHistoryNavigatorProvider: MumentHistoryNavigatorProvider
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    @Inject
    lateinit var declareNavigatorProvider: DeclareNavigatorProvider


    @Inject
    lateinit var reportMumentNavigatorProvider: ReportMumentNavigatorProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMumentDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onResume() {
        super.onResume()
        updateModifiedMumentDetail()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mumentDetailViewModel = viewModel
        getResultText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    it.data?.getParcelableExtra<MusicInfoEntity>("MUSIC_INFO")?.let { music ->
                        it.data?.getStringExtra(MUMENT_ID)?.let { mumentId ->
                            viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(mumentId))
                            viewModel.emitEvent(MumentDetailEvent.ReceiveMusicInfo(music))
                        } ?: musicDetailNavigatorProvider.fromMumentDetailToMusicDetail(music)
                    }
                }
            }
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
        binding.tvLikeCount.setOnClickListener {
            likeUsersNavigatorProvider.toLikeUsers(ArrayList(viewModel.viewState.value.likeUsers))
        }
    }

    private fun receiveMumentInfo() {
        arguments?.getString(MUMENT_ID)?.let {
            viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(it))
        }
        arguments?.getString(MUSIC_INFO_ENTITY)?.let {
            Log.e("MUSIC INFO", it)
            val musicInfo = Gson().fromJson(it, MusicInfoEntity::class.java)
            Log.e("MUSIC INFO GSON", musicInfo.toString())
            viewModel.emitEvent(MumentDetailEvent.ReceiveMusicInfo(musicInfo))
        }
    }

    private fun updateModifiedMumentDetail() {
        viewModel.viewState.value.let { state ->
            if (state.musicInfo != null && state.requestMumentId.isNotEmpty()) {
                viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(state.requestMumentId))
            }
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
            if (it.historyCount > 0) showMumentHistory()
        }
    }

    private fun receiveEffect() {
        collectFlowWhenStarted(viewModel.effect) { effect ->
            when (effect) {
                MumentDetailSideEffect.PopBackStack -> findNavController().popBackStack()
                MumentDetailSideEffect.SuccessMumentDeletion -> findNavController().popBackStack()
                MumentDetailSideEffect.SuccessBlockUser -> {
                    requireContext().showToast("차단이 완료되었습니다.")
                    findNavController().popBackStack()
                }
                MumentDetailSideEffect.OpenEditOrDeleteMumentDialog -> {
                    showEditOrDeleteMumentDialog()
                }
                is MumentDetailSideEffect.NavToEditMument -> {
                    editMumentNavigatorProvider.editMument(
                        mumentId = effect.mumentId,
                        music = effect.music,
                        mumentModifyEntity = effect.mumentModifyEntity
                    )
                }
                MumentDetailSideEffect.OpenDeleteMumentDialog -> showMumentDeletionDialog()

                MumentDetailSideEffect.OpenBlockOrReportBottomSheet -> {
                    showBlockOrReportBottomSheet()
                }
                is MumentDetailSideEffect.NavToReportMument -> {
                    declareNavigatorProvider.moveDeclare(effect.mumentId)
                }

                MumentDetailSideEffect.OpenBlockUserDialog -> showBlockUserDialog()

                is MumentDetailSideEffect.NavToMusicDetail -> musicDetailNavigatorProvider.fromMumentDetailToMusicDetail(
                    effect.music
                )
                is MumentDetailSideEffect.NavToMumentHistory -> {
                    //여기 나중에 수정 좀 부탁드립니당!!
                    //현재 effect.musicId만 받아와짐
                    viewModel.viewState.value.musicInfo?.toMusic()?.let { music ->
                        viewModel.viewState.value.mument?.writerInfo?.userId?.toInt()
                            ?.let { userId ->
                                getResultText.launch(
                                    Intent(
                                        requireActivity(),
                                        HistoryActivity::class.java
                                    ).apply {
                                        putExtra("music", music)
                                        putExtra("userId", userId)
                                    })
                            }
                    }
                }
                is MumentDetailSideEffect.Toast -> requireContext().showToast(
                    resources.getString(
                        effect.message
                    )
                )
                is MumentDetailSideEffect.ToastString -> {
                    requireContext().showToast(effect.message)
                }
                is MumentDetailSideEffect.OpenShareMumentDialog -> {
                    Log.e("OpenShareMumentDialog", effect.toString())
                    openShareMumentDialog(effect.mument, effect.musicInfo)
                }
                is MumentDetailSideEffect.NavToInstagram -> {
                    navToInstagram(effect.imageUri)
                }
            }
        }
    }

    private fun showMumentHistory() {
        binding.tvGoToHistory.run {
            if (visibility == View.GONE) applyVisibilityAnimation(
                isUpward = true,
                reveal = true,
                durationTime = 700,
                delay = 150
            )
        }
    }

    private fun changeLikeStatus() {
        binding.cbHeart.setOnClickListener {
            viewModel.emitEvent(
                if (binding.cbHeart.isChecked) MumentDetailEvent.OnClickLikeMument else MumentDetailEvent.OnClickUnLikeMument
            )
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
                    viewModel.viewState.value.let { viewState ->
                        viewModel.emitEvent(
                            MumentDetailEvent.SelectMumentEditType(
                                mumentId = viewState.requestMumentId,
                                music =
                                viewState.musicInfo!!.let {
                                    RecentSearchData(
                                        _id = it.id,
                                        artist = it.artist,
                                        image = it.thumbnail,
                                        name = it.name,
                                        createAt = null
                                    )
                                },
                                mumentModifyEntity =
                                MumentModifyEntity(
                                    content = viewState.mument?.content.toString(),
                                    feelingTag = viewState.mument?.emotionalTags?.map { it.tagIdx }
                                        ?: listOf(),
                                    impressionTag = viewState.mument?.impressionTags?.map { it.tagIdx }
                                        ?: listOf(),
                                    isFirst = viewState.mument?.isFirst?.tagIdx == 1,
                                    isPrivate = viewState.mument?.isPrivate!!
                                )
                            )
                        )
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
                when (type) {
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
            viewModel.viewState.value.musicInfo?.let { music ->
                viewModel.emitEvent(MumentDetailEvent.OnClickAlum(music))
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
        Log.e("openShareMumentDialog", "$mument $musicInfoEntity")
        MumentToShareDialogFragment { file, uri ->
            Log.e("SHARE", "$file $uri")
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

    companion object {
        private const val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"
        private const val SHARE_INSTAGRAM_STORY_ID = "com.instagram.share.ADD_TO_STORY"
        private const val APP_ID_KEY = "source_application"
        private const val STICKER_ASSET_KEY = "interactive_asset_uri"
        private const val STORY_TOP_BACKGROUND_COLOR_KEY = "top_background_color"
        private const val STORY_BOTTOM_BACKGROUND_COLOR_KEY = "bottom_background_color"
        private const val STORY_BACKGROUND_COLOR_VALUE = "#989898"
        const val INSTA_STORY_BACKGROUND_COLOR = "#989898"
        private const val TYPE_IMAGE = "image/*"
    }
}