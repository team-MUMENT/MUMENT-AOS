package com.mument_android.detail.music

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.angdroid.navigation.HistoryNavigatorProvider
import com.angdroid.navigation.MoveRecordProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.angdroid.navigation.MusicDetailNavigatorProvider
import com.mument_android.core.util.Constants
import com.mument_android.core.util.Constants.MUMENT_ID
import com.mument_android.core.util.Constants.MUSIC_INFO_ENTITY
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.RecyclerviewItemDivider
import com.mument_android.core_dependent.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.detail.databinding.FragmentMusicDetailBinding
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.detail.music.MusicDetailContract.MusicDetailEffect
import com.mument_android.detail.music.MusicDetailContract.MusicDetailEvent
import com.mument_android.domain.entity.music.MusicInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MusicDetailFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMusicDetailBinding>()
    private val musicDetailViewModel: MusicDetailViewModel by viewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @Inject
    lateinit var musicDetailNavigatorProvider: MusicDetailNavigatorProvider

    @Inject
    lateinit var recordProvider: MoveRecordProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

    @Inject
    lateinit var historyNavigatorProvider: HistoryNavigatorProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                musicDetailViewModel.emitEvent(MusicDetailEvent.OnClickBackButton)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMusicDetailBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicDetailViewModel = musicDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        clickBackButton()
        setMyMumentTagList()
        setEntireMumentListAdapter()
        updateView()
        collectEffect()
        receiveMusicId()
        changeMumentSortType()
        moveToHistoryFragment()
    }

    private fun clickBackButton() {
        binding.ivBack.setOnClickListener {
            musicDetailViewModel.emitEvent(MusicDetailEvent.OnClickBackButton)
        }
    }

    private fun receiveMusicId() {
        arguments?.getParcelable<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ReceiveRequestMusicInfo(it))
        }
        arguments?.getString(Constants.START_NAV_KEY)?.let {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ReceiveStartNav(it))
        }
    }

    private fun updateView() {
        collectFlowWhenStarted(musicDetailViewModel.viewState) { state ->
            with(binding) {
                (layoutMyMument.rvMumentTags.adapter as MumentTagListAdapter).submitList(state.myMumentInfo?.tags)
                (rvEveryMuments.adapter as MusicDetailMumentListAdapter).submitList(state.mumentList)
                changeSortTypeSelectedTheme(state.mumentSortType.sort)
            }
        }
        binding.layoutMyMument.clRoot.click {
            musicDetailViewModel.viewState.value.myMumentInfo?.let { mumentInfo ->
                musicDetailViewModel.viewState.value.musicInfo?.let { musicInfoEntity ->
                    mumentDetailNavigatorProvider.musicDetailToMumentDetail(
                        mumentInfo.mumentId,
                        musicInfo = musicInfoEntity
                    )
                }
            }
        }

        // TODO 해당 부분 리펙토링 해야합니당. 현재 해당 뷰 안 보여서 테스트도 못 해봐씀, 불필요하게 길어유
        binding.layoutMyMument.laLikeMumentDetail.click {
            if (musicDetailViewModel.viewState.value.myMumentInfo != null) {
                if (binding.layoutMyMument.laLikeMumentDetail.progress == 0F) {
                    binding.layoutMyMument.laLikeMumentDetail.playAnimation()
                    musicDetailViewModel.emitEvent(
                        MusicDetailEvent.CheckLikeMument(
                            musicDetailViewModel.viewState.value.myMumentInfo!!.mumentId
                        )
                    )
                } else {
                    musicDetailViewModel.emitEvent(
                        MusicDetailEvent.UnCheckLikeMument(
                            musicDetailViewModel.viewState.value.myMumentInfo!!.mumentId
                        )
                    )
                }
            }
        }
        binding.layoutMyMument.llTouchArea.click {
            if (musicDetailViewModel.viewState.value.myMumentInfo != null) {
                if (binding.layoutMyMument.laLikeMumentDetail.progress == 0F) {
                    binding.layoutMyMument.laLikeMumentDetail.playAnimation()
                    musicDetailViewModel.emitEvent(
                        MusicDetailEvent.CheckLikeMument(
                            musicDetailViewModel.viewState.value.myMumentInfo!!.mumentId
                        )
                    )
                } else {
                    musicDetailViewModel.emitEvent(
                        MusicDetailEvent.UnCheckLikeMument(
                            musicDetailViewModel.viewState.value.myMumentInfo!!.mumentId
                        )
                    )
                }
            }
        }
    }

    private fun collectEffect() {
        collectFlowWhenStarted(musicDetailViewModel.effect) { effect ->
            when (effect) {
                is MusicDetailEffect.ShowToast -> requireContext().showToast(effect.msg)
                is MusicDetailEffect.PopBackStack -> {
                    musicDetailNavigatorProvider.musicDetailPopBackStack(effect.startNav)
                }
                MusicDetailEffect.CompleteLikeMument -> {
                    binding.layoutMyMument.mument =
                        musicDetailViewModel.viewState.value.myMumentInfo
                }
            }
        }
    }

    private fun setMyMumentTagList() {
        binding.layoutMyMument.rvMumentTags.adapter = MumentTagListAdapter()
    }

    private fun setEntireMumentListAdapter() {
        binding.rvEveryMuments.run {
            addItemDecoration(RecyclerviewItemDivider(0, 15.dpToPx(requireContext()), IS_VERTICAL))
            adapter = MusicDetailMumentListAdapter(object : MumentClickListener {
                override fun showMumentDetail(mumentId: String) {
                    musicDetailViewModel.viewState.value.musicInfo?.let { musicInfo ->
                        mumentDetailNavigatorProvider.musicDetailToMumentDetail(mumentId, musicInfo)
                    }
                }

                override fun likeMument(mumentId: String) {
                    musicDetailViewModel.emitEvent(MusicDetailEvent.CheckLikeItemMument(mumentId))
                }

                override fun cancelLikeMument(mumentId: String) {
                    musicDetailViewModel.emitEvent(MusicDetailEvent.UnCheckLikeItemMument(mumentId))
                }
            })
        }
    }

    private fun changeMumentSortType() {
        binding.tvSortLikeCount.setOnClickListener {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ClickSortByLikeCount)
        }
        binding.tvSortLatest.setOnClickListener {
            musicDetailViewModel.emitEvent(MusicDetailEvent.ClickSortByLatest)
        }
    }

    private fun changeSortTypeSelectedTheme(sort: String) {
        binding.tvSortLatest.changeSelectedSortTheme(sort)
        binding.tvSortLikeCount.changeSelectedSortTheme(sort)
    }

    private val getResultText =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            it.data?.getParcelableExtra<MusicInfoEntity>(MUSIC_INFO_ENTITY)?.let { music ->
                it.data?.getStringExtra(MUMENT_ID)?.let { mumentId ->
                    mumentDetailNavigatorProvider.musicDetailToMumentDetail(mumentId, music)
                }
            }
        }
    }

    private fun moveToHistoryFragment() {
        binding.tvShowMyHistory.setOnClickListener {
            musicDetailViewModel.viewState.value.musicInfo
                ?.let {
                    musicDetailViewModel.viewState.value.myMumentInfo?.user?.userId?.let { userId ->
                        getResultText.launch(
                            Intent(
                                requireActivity(),
                                HistoryActivity::class.java
                            ).apply {
                                putExtra("music", it.toMusic())
                                putExtra("userId", userId.toInt())
                            })
                    }
                }
        }
    }

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

    override fun onDestroyView() {
        onBackPressedCallback.remove()
        super.onDestroyView()
    }

    companion object {
        const val MUSIC_ID = "MUSIC_ID"
    }
}