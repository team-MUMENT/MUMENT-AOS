package com.mument_android.detail.mument.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.navigation.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.mument_android.core.util.Constants.FROM_NOTIFICATION_TO_MUMENT_DETAIL
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core.util.Constants.START_NAV_KEY
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ext.setOnSingleClickListener
import com.mument_android.core_dependent.ui.MumentDialogBuilder
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.*
import com.mument_android.core_dependent.util.RecyclerviewItemDivider.Companion.IS_GRIDLAYOUT
import com.mument_android.core_dependent.util.ViewUtils.applyVisibilityAnimation
import com.mument_android.core_dependent.util.ViewUtils.showToast
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
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @Inject
    lateinit var editMumentNavigatorProvider: EditMumentNavigatorProvider

    @Inject
    lateinit var musicDetailNavigatorProvider: MusicDetailNavigatorProvider

    @Inject
    lateinit var likeUsersNavigatorProvider: LikeUsersNavigatorProvider

    @Inject
    lateinit var mumentHistoryNavigatorProvider: MumentHistoryNavigatorProvider

    @Inject
    lateinit var declareNavigatorProvider: DeclareNavigatorProvider

    @Inject
    lateinit var reportMumentNavigatorProvider: ReportMumentNavigatorProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.emitEvent(MumentDetailEvent.OnClickBackButton)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

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
        binding.tvLikeCount.setOnSingleClickListener {
            viewModel.emitEvent(MumentDetailEvent.OnClickLikeCount)
        }
    }

    private fun receiveMumentInfo() {
        arguments?.getString(MUMENT_ID)?.let {
            viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(it))
        }
        arguments?.getString(MUSIC_INFO_ENTITY)?.let {
            val musicInfo = Gson().fromJson(it, MusicInfoEntity::class.java)
            viewModel.emitEvent(MumentDetailEvent.ReceiveMusicInfo(musicInfo))
        }
        arguments?.getString(START_NAV_KEY)?.let {
            Log.e("START_NAV_KEY", it)
            viewModel.emitEvent(MumentDetailEvent.ReceiveStartNav(it))
        } ?: viewModel.emitEvent(MumentDetailEvent.ReceiveStartNav(""))
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
                MumentDetailSideEffect.ShowDeletedMumentAlert -> {
                    showDeletedMumentDialog()
                }
                MumentDetailSideEffect.PopBackStack -> {
                    val startNav = viewModel.viewState.value.navStart
                    mumentDetailNavigatorProvider.mumentDetailPopBackStack(startNav)
                }
                MumentDetailSideEffect.SuccessMumentDeletion -> {
                    val startNav = viewModel.viewState.value.navStart
                    mumentDetailNavigatorProvider.mumentDetailPopBackStack(startNav)
                }
                MumentDetailSideEffect.SuccessBlockUser -> {
                    requireContext().showToast("차단이 완료되었습니다.")
                    val startNav = viewModel.viewState.value.navStart
                    mumentDetailNavigatorProvider.mumentDetailPopBackStack(startNav)
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
                is MumentDetailSideEffect.NavToLikeUserListView -> {
                    likeUsersNavigatorProvider.toLikeUsers(effect.mumentId)
                }
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
                                mumentHistoryLauncher.launch(
                                    Intent(requireActivity(), HistoryActivity::class.java).apply {
                                        putExtra("music", music)
                                        putExtra("userId", userId)
                                        if (viewModel.viewState.value.navStart == FROM_NOTIFICATION_TO_MUMENT_DETAIL) {
                                            putExtra(
                                                START_NAV_KEY,
                                                FROM_NOTIFICATION_TO_MUMENT_DETAIL
                                            )
                                        }
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
                    openShareMumentDialog(effect.mument, effect.musicInfo)
                }
                is MumentDetailSideEffect.NavToInstagram -> {
                    navToInstagram(effect.imageUri)
                }
            }
        }
    }

    private fun showDeletedMumentDialog() {
        MumentDialogBuilder()
            .setHeader("삭제된 뮤멘트입니다.")
            .setBody("이용에 불편을 드려 죄송합니다.")
            .setAllowListener("확인") {
                val startNav = viewModel.viewState.value.navStart
                mumentDetailNavigatorProvider.mumentDetailPopBackStack(startNav)
            }
            .setCancelListener("") {}
            .build()
            .show(childFragmentManager, this.tag)
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
        binding.laLike.click {
            if (binding.laLike.progress == 0F) {
                binding.laLike.playAnimation()
                viewModel.emitEvent(
                    MumentDetailEvent.OnClickLikeMument
                )
            } else {
                viewModel.emitEvent(
                    MumentDetailEvent.OnClickUnLikeMument
                )
            }
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
                //뮤멘트 히스토리 페이지에 진입 경로
                //내 뮤멘트 일 때
                if(viewModel.viewState.value.isWriter) {
                    FirebaseAnalyticsUtil.firebaseLog(
                        "mument_history_view",
                        "journey",
                        "from_my_mument_detail"
                    )
                } else {
                    FirebaseAnalyticsUtil.firebaseLog(
                        "mument_history_view",
                        "journey",
                        "from_other_mument_detail"
                    )
                }
                viewModel.emitEvent(MumentDetailEvent.OnClickHistory(musicId))
            }
        }
    }

    private fun shareMumentOnInstagram() {
        binding.ivShare.setOnClickListener {
            //인스타 클릭 GA
            FirebaseAnalyticsUtil.firebaseLog(
                "share_instagram",
                "count",
                "click_instagram"
            )
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

    private val mumentHistoryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                it.data?.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let { music ->
                    it.data?.getStringExtra(START_NAV_KEY)
                        .takeIf { it == FROM_NOTIFICATION_TO_MUMENT_DETAIL }?.let {
                        viewModel.emitEvent(MumentDetailEvent.ReceiveStartNav(it))
                    }
                    it.data?.getStringExtra(MUMENT_ID)?.let { mumentId ->
                        viewModel.emitEvent(MumentDetailEvent.ReceiveMumentId(mumentId))
                        viewModel.emitEvent(MumentDetailEvent.ReceiveMusicInfo(music))
                    } ?: musicDetailNavigatorProvider.fromMumentDetailToMusicDetail(music)
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    companion object {
        private const val INSTAGRAM_PACKAGE_NAME = "com.instagram.android"
        private const val SHARE_INSTAGRAM_STORY_ID = "com.instagram.share.ADD_TO_STORY"
        private const val APP_ID_KEY = "source_application"
        private const val STICKER_ASSET_KEY = "interactive_asset_uri"
        private const val STORY_TOP_BACKGROUND_COLOR_KEY = "top_background_color"
        private const val STORY_BOTTOM_BACKGROUND_COLOR_KEY = "bottom_background_color"
        private const val STORY_BACKGROUND_COLOR_VALUE = "#D8D8D8"
        const val INSTA_STORY_BACKGROUND_COLOR = "#989898"
        private const val TYPE_IMAGE = "image/*"
    }
}