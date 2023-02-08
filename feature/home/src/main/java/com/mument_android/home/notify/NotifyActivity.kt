package com.mument_android.home.notify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.TransitionMode
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.home.adapters.NotifyAdapter
import com.mument_android.home.databinding.ActivityNotifyBinding
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.NotifyEvent
import com.mument_android.home.notify.NotifyContract.NotifySideEffect
import com.mument_android.home.search.SearchActivity
import com.mument_android.home.viewmodels.NotifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifyActivity : BaseActivity<ActivityNotifyBinding>(
    inflate = ActivityNotifyBinding::inflate, mode = TransitionMode.HORIZONTAL
) {
    private lateinit var notifyAdapter: NotifyAdapter
    private val notifyViewModel by viewModels<NotifyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appBarClickListener()
        adapterSetting()
        receiveEffect()
        collectViewState()
    }

    private fun collectViewState() {
        collectFlowWhenStarted(notifyViewModel.notifyViewState) { notifyViewState ->
            with(notifyViewState) {
                notifyList?.let { notifies ->
                    Log.e("NOTIFES", notifies.toString())
                    notifyAdapter.submitList(notifies)
                }
            }
        }
    }

    private fun receiveEffect() {
        collectFlowWhenStarted(notifyViewModel.notifyEffect) { notifySideEffect ->
            when (notifySideEffect) {
                NotifySideEffect.PopBackStack -> {
                    finish()
                }
                NotifySideEffect.AllReadSuccess -> {
                    Log.e("Success", "AllRead Success")
                }
                NotifySideEffect.NavToSetting -> {
                    //Nav to Setting
                }
                is NotifySideEffect.DeleteNotify -> {
                    notifyViewModel.deleteNotify(notifySideEffect.notify)
                }
                is NotifySideEffect.NavToMumentDetail -> {
                    moveToMumentDetail(notifySideEffect.notify)
                }
                is NotifySideEffect.Toast -> {
                    showToast(notifySideEffect.message)
                }
                is NotifySideEffect.NavToNotice -> {
                    moveToNoticeView(notifySideEffect.notify)
                }
            }
        }
    }

    private fun moveToMumentDetail(notify: Notify) {
        //Move MumentDetail
        startActivity(Intent(this, SearchActivity::class.java))
        finish()
    }

    private fun moveToNoticeView(notify: Notify) {
        //Move Notice
    }

    private fun adapterSetting() {
        notifyAdapter = NotifyAdapter({ notifyViewModel.emitEvent(NotifyEvent.OnClickNotify(it)) },
            { notifyViewModel.emitEvent(NotifyEvent.OnDeleteNotify(it)) })
        binding.rvNotifyList.adapter = notifyAdapter
    }

    private fun appBarClickListener() {
        binding.appbarNotify.ivNotifyBack.setOnClickListener {
            notifyViewModel.emitEvent(NotifyEvent.OnClickBackBtn)
        }
        binding.appbarNotify.ivNotifySetting.setOnClickListener {
            notifyViewModel.emitEvent(NotifyEvent.OnClickSettingBtn)
        }
    }
}