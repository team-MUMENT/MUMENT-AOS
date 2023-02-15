package com.mument_android.detail.history

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.paging.PagingData
import com.angdroid.navigation.MoveFromHistoryToDetail
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.detail.databinding.ActivityHistoryBinding
import com.mument_android.domain.entity.history.MumentHistory
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
            adapter.submitData(paging)
        }
    }

    private fun setListener() {
        binding.clTouch.click { moveToMusicDetail() }
        binding.ivAlbum.click { moveToMusicDetail() }
        binding.ivBtnBack.click { moveToMusicDetail() }
        binding.tvLatestOrder.click {
            historyViewModel.changeSortType("Y")
        }
        binding.tvOldestOrder.click {
            historyViewModel.changeSortType("N")
        }
    }

    private fun moveToMumentDetail(mumentId: String) {
        val music = historyViewModel.music.value.toMusicInfoEntity()
        moveFromHistoryToDetail.moveMumentDetail(mumentId, music)
    }

    private fun moveToMusicDetail() {
        val music = historyViewModel.music.value.toMusicInfoEntity()
        moveFromHistoryToDetail.moveMusicDetail(music)
    }

    private fun likeMument(mumentId: String) {
        historyViewModel.likeMument(mumentId)
    }

    private fun deleteLikeMument(mumentId: String) {
        historyViewModel.cancelLikeMument(mumentId)
    }

    private fun collectSortType() {
        collectFlowWhenStarted(historyViewModel.selectSortType) { sort ->
            adapter = HistoryListAdapter(::moveToMumentDetail, {
                likeMument(it)
            }, { deleteLikeMument(it) })
            binding.rcHistory.adapter = adapter
            binding.tvLatestOrder.isSelected = sort == "Y"
            binding.tvOldestOrder.isSelected = sort == "N"
            //이거 나중에 수정,,
        }
    }
}
