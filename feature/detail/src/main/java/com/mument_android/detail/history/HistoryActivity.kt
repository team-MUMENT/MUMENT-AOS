package com.mument_android.detail.history

import android.os.Bundle
import androidx.activity.viewModels
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
        setAdapter()
        collectSortType()
        collectFlowWhenStarted(historyViewModel.fetchHistory) { paging ->
            adapter.submitData(paging)
        }
    }

    private fun setListener() {
        binding.clTouch.click { finish() }
        binding.ivAlbum.click { finish() }
        binding.ivBtnBack.click { finish() }
        binding.tvLatestOrder.click {
            historyViewModel.changeSortType("Y")
        }
        binding.tvOldestOrder.click {
            historyViewModel.changeSortType("N")
        }
    }

    private fun setAdapter() {
        adapter = HistoryListAdapter(::moveToMumentDetail)
        binding.rcHistory.adapter = adapter
    }

    private fun moveToMumentDetail(mumentHistory: MumentHistory) {
        val music = historyViewModel.music.value.toMusicInfoEntity()
        moveFromHistoryToDetail.moveMumentDetail(mumentHistory._id.toString(), music)
    }

    private fun collectSortType() {
        collectFlowWhenStarted(historyViewModel.selectSortType) { sort ->
            binding.tvLatestOrder.isSelected = sort == "Y"
            binding.tvOldestOrder.isSelected = sort == "N"
            //이거 나중에 수정,,
        }
    }
}
