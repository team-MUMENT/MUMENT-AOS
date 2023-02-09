package com.mument_android.detail.music

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angdroid.navigation.HistoryNavigatorProvider
import com.angdroid.navigation.MoveRecordProvider
import com.angdroid.navigation.MoveToAlarmFragmentProvider
import com.angdroid.navigation.MumentDetailNavigatorProvider
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.ui.MumentTagListAdapter
import com.mument_android.core_dependent.util.AutoClearedValue
import com.mument_android.core_dependent.util.RecyclerviewItemDivider
import com.mument_android.core_dependent.util.RecyclerviewItemDivider.Companion.IS_VERTICAL
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.detail.databinding.FragmentMusicDetailBinding
import com.mument_android.detail.history.HistoryActivity
import com.mument_android.detail.mument.fragment.MumentDetailFragment
import com.mument_android.detail.mument.listener.MumentClickListener
import com.mument_android.detail.music.MusicDetailContract.MusicDetailEffect
import com.mument_android.detail.music.MusicDetailContract.MusicDetailEvent
import com.mument_android.detail.util.SuggestionNotifyAccessDialogFragment
import com.mument_android.domain.entity.music.MusicInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MusicDetailFragment() : Fragment() {
    private var binding by AutoClearedValue<FragmentMusicDetailBinding>()
    private val musicDetailViewModel: MusicDetailViewModel by viewModels()
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    @Inject
    lateinit var recordProvider: MoveRecordProvider

    @Inject
    lateinit var mumentDetailNavigatorProvider: MumentDetailNavigatorProvider

    @Inject
    lateinit var historyNavigatorProvider: HistoryNavigatorProvider

    @Inject
    lateinit var moveToAlarmFragmentProvider: MoveToAlarmFragmentProvider

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

        getResultText =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    it.data?.getParcelableExtra<MusicInfoEntity>("MUSIC_INFO")?.let { music ->
                        it.data?.getStringExtra(MumentDetailFragment.MUMENT_ID)?.let { mumentId ->
                            mumentDetailNavigatorProvider.musicDeatilToMumentDetail(
                                mumentId,
                                music
                            )
                        }
                    }
                }
            }
        SuggestionNotifyAccessDialogFragment.newInstance {
            if (it) {
                moveToAlarmFragmentProvider.moveAlarmFromMusic()
            }
        }.show(parentFragmentManager, "Suggestion")
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
            Log.e("music info", "${it}")
            musicDetailViewModel.emitEvent(MusicDetailEvent.ReceiveRequestMusicInfo(it))
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
    }

    private fun collectEffect() {
        collectFlowWhenStarted(musicDetailViewModel.effect) { effect ->
            when (effect) {
                is MusicDetailEffect.ShowToast -> requireContext().showToast(effect.msg)
                MusicDetailEffect.PopBackStack -> {
                    findNavController().popBackStack()
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
                        mumentDetailNavigatorProvider.musicDeatilToMumentDetail(mumentId, musicInfo)
                    }
                }

                override fun likeMument(mumentId: String) {
                    musicDetailViewModel.emitEvent(MusicDetailEvent.CheckLikeMument(mumentId))
                }

                override fun cancelLikeMument(mumentId: String) {
                    musicDetailViewModel.emitEvent(MusicDetailEvent.UnCheckLikeMument(mumentId))
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
                                putExtra("userId", userId)
                            })

                    }
                }
        }
    }

    private fun AppCompatTextView.changeSelectedSortTheme(selectedSort: String) {
        isSelected = selectedSort == text.toString()
    }

    companion object {
        const val MUSIC_ID = "MUSIC_ID"
        const val MUSIC_INFO_ENTITY = "MUSIC_INFO_ENTITY"
    }
}