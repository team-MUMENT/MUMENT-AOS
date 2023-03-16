package com.mument_android.detail.history

import android.os.Bundle
import androidx.activity.viewModels
import com.angdroid.navigation.MoveFromHistoryToDetail
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.detail.databinding.ActivityHistoryBinding
import com.mument_android.detail.mument.listener.StackProvider
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryActivity :
    BaseActivity<ActivityHistoryBinding>(inflate = ActivityHistoryBinding::inflate) {
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryListAdapter

    @Inject
    lateinit var moveFromHistoryToDetail: MoveFromHistoryToDetail

    @Inject
    lateinit var stackProvider: StackProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.historyviewmodel = historyViewModel

        intent.getParcelableExtra<Music>("music")?.let {
            historyViewModel.changeMusicId(it)
        }
        intent.getIntExtra("userId", 0).let {
            historyViewModel.setUserId(it)
        }

        setListener()
        collectSortType()
        collectFlowWhenStarted(historyViewModel.fetchHistory) { paging ->
            adapter.submitList(paging)
        }
    }

    private fun setListener() {
        binding.clTouch.click {
            stackProvider.clearBackStack()
            moveToMusicDetail(false)
        }
        binding.ivAlbum.click {
            stackProvider.clearBackStack()
            moveToMusicDetail(false)
        }
        binding.ivBtnBack.click {
            moveFromHistoryToDetail.popBackToMain()
        }
        binding.tvLatestOrder.click {
            historyViewModel.changeSortType("Y")
        }
        binding.tvOldestOrder.click {
            historyViewModel.changeSortType("N")
        }
    }

    private fun moveToMumentDetail(mumentId: String, popBackStack: Boolean) {
        val music = historyViewModel.music.value.toMusicInfoEntity()
        moveFromHistoryToDetail.moveMumentDetail(mumentId, music, popBackStack)
    }

    private fun moveToMusicDetail(popBackStack: Boolean) {
        val music = historyViewModel.music.value.toMusicInfoEntity()
        moveFromHistoryToDetail.moveMusicDetail(music, popBackStack)
    }

    private fun likeMument(mumentId: String) {
        historyViewModel.likeMument(mumentId)
    }

    private fun cancelLikeMument(mumentId: String) {
        historyViewModel.cancelLikeMument(mumentId)
    }

    private fun collectSortType() {
        collectFlowWhenStarted(historyViewModel.selectSortType) { sort ->
            adapter = HistoryListAdapter(
                itemClickListener = { moveToMumentDetail(it, false) },
                likeMument = { likeMument(it) },
                cancelLikeMument = { cancelLikeMument(it) })
            binding.rcHistory.adapter = adapter
            binding.tvLatestOrder.isSelected = sort == "Y"
            binding.tvOldestOrder.isSelected = sort == "N"
            //이거 나중에 수정,,
        }
    }

    override fun onBackPressed() {
        moveFromHistoryToDetail.popBackToMain()
        super.onBackPressed()
    }
}
