package com.mument_android.home.notify

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import com.angdroid.navigation.MoveNotifyNavigatorProvider
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.TransitionMode
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.home.adapters.NotifyAdapter
import com.mument_android.home.databinding.ActivityNotifyBinding
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.NotifyEvent
import com.mument_android.home.notify.NotifyContract.NotifySideEffect
import com.mument_android.home.viewmodels.NotifyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotifyActivity : BaseActivity<ActivityNotifyBinding>(
    inflate = ActivityNotifyBinding::inflate, mode = TransitionMode.HORIZONTAL
) {
    private lateinit var notifyAdapter: NotifyAdapter
    private val notifyViewModel by viewModels<NotifyViewModel>()

    @Inject
    lateinit var moveNotifyNavigatorProvider: MoveNotifyNavigatorProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.notifyViewModel = notifyViewModel
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
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
                    moveToAlarmSetting()
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
        notify.like.toMusicInfoEntity()?.let {
            moveNotifyNavigatorProvider.moveToMumentDetail(notify.linkId.toString(), it)
        }
    }

    private fun moveToNoticeView(notify: Notify) {
        //Move Notice
        moveNotifyNavigatorProvider.moveToNoticeDetail(notify.linkId)
    }

    private fun moveToAlarmSetting() {
        Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //버전에 따른 알림 설정
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            } else {
                putExtra("app_package", packageName)
                putExtra("app_uid", applicationInfo.uid)
            }
        }.also { destination ->
            startActivity(destination)
        }
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