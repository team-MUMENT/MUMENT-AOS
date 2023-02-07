package com.mument_android.detail.history

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.detail.databinding.ActivityHistoryBinding
import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : BaseActivity<ActivityHistoryBinding>(inflate = ActivityHistoryBinding::inflate) {
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryListAdapter

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
        val music = historyViewModel.music.value?.let {
            MusicInfoEntity(it._id, it.name, it.artist, it.image)
        }
        //History로 갈 수 있는 depth가 MumentDetail->Music Detail or Music Detail로만 가능하므로 ResultLauncher 달아놔야할듯,, Activity라서,,
        finish()
    }

    private fun collectSortType() {
        collectFlowWhenStarted(historyViewModel.selectSortType) { sort ->
            binding.tvLatestOrder.isSelected = sort == "Y"
            binding.tvOldestOrder.isSelected = sort == "N"
            //이거 나중에 수정,,
        }
    }
}
