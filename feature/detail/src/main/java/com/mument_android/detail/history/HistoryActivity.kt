package com.mument_android.detail.history

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.angdroid.navigation.MoveFromHistoryToDetail
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.click
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.parcelable
import com.mument_android.detail.databinding.ActivityHistoryBinding
import com.angdroid.navigation.StackProvider
import com.mument_android.core_dependent.util.LikeMumentListener
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

    private val historyKillBroadcastReceiver = HistoryKillBroadCastReceiver()

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onResume() {
        super.onResume()
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveFromHistoryToDetail.popBackToMain()
            }
        }
        if (::onBackPressedCallback.isInitialized) {
            onBackPressedDispatcher.addCallback(onBackPressedCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
        unregisterReceiver(historyKillBroadcastReceiver)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.historyviewmodel = historyViewModel
        intent.parcelable<Music>("music")?.let {
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
        registerReceiver(historyKillBroadcastReceiver, IntentFilter("KILL_HISTORY"))
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.parcelable<Music>("music")?.let {
            Log.e("GET", it.toString())
            historyViewModel.changeMusicId(it)
        }
        intent?.getIntExtra("userId", 0)?.let {
            Log.e("GET", it.toString())
            historyViewModel.setUserId(it)
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
        finish()
    }

    private fun likeCallbackMument(mumentId: String, resultCallback: (Boolean) -> Unit) {
        historyViewModel.likeMument(mumentId) {
            resultCallback.invoke(it)
        }
    }

    private fun cancelLikeCallBackMument(mumentId: String, resultCallback: (Boolean) -> Unit) {
        historyViewModel.cancelLikeMument(mumentId) {
            resultCallback.invoke(it)
        }
    }

    private fun collectSortType() {
        collectFlowWhenStarted(historyViewModel.selectSortType) { sort ->
            adapter = HistoryListAdapter(
                itemClickListener = { moveToMumentDetail(it, false) },
                likeMumentListener = object : LikeMumentListener {
                    override fun likeMument(mumetId: String, resultCallback: (Boolean) -> Unit) {
                        likeCallbackMument(mumetId, resultCallback)
                    }

                    override fun cancelLikeMument(mumetId: String, resultCallback: (Boolean) -> Unit) {
                        cancelLikeCallBackMument(mumetId, resultCallback)
                    }
                }
            )
            binding.rcHistory.adapter = adapter
            binding.tvLatestOrder.isSelected = sort == "Y"
            binding.tvOldestOrder.isSelected = sort == "N"
            //이거 나중에 수정,,
        }
    }

    inner class HistoryKillBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1?.action == "KILL_HISTORY") {
                finish()
            }
        }
    }
}
