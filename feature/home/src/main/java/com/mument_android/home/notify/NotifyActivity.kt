package com.mument_android.home.notify

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.TransitionMode
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.home.R
import com.mument_android.home.adapters.NotifyAdapter
import com.mument_android.home.databinding.ActivityNotifyBinding
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.*
import com.mument_android.home.search.SearchActivity
import com.mument_android.home.util.NotifyType
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
        binding.lifecycleOwner = this
        appBarClickListener()
        adapterSetting()
        receiveEffect()
        collectViewState()
    }

    private fun collectViewState() {
        collectFlowWhenStarted(notifyViewModel.notifyViewState) { notifyViewState ->
            with(notifyViewState) {
                notifyList?.let { notifies ->
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
        notifyAdapter.submitList(
            listOf(
                Notify(
                    id = "",
                    createdAt = "02/05 09:10",
                    "",
                    1,
                    "예진님이 POPPOPOPOPOPOPOPOPOPOPOPOPOP에 쓴 뮤멘트를 좋아합니다.", //단어가 길면 자동 줄바꿈이 일어나므로 유니코드 변경, 나중에 전반적으로 수정할 예정,
                    NotifyType.LIKE
                ),
                Notify(
                    id = "",
                    createdAt = "02/05 09:10",
                    "",
                    1,
                    "예진님이 POPPOPOPOPOPOPOPOPOPOPOPOPOP에 쓴 뮤멘트를 좋아합니다.",
                    NotifyType.LIKE
                ),
            )
        )
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