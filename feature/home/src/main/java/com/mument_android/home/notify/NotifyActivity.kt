package com.mument_android.home.notify

import android.os.Bundle
import androidx.activity.viewModels
import com.mument_android.core_dependent.base.BaseActivity
import com.mument_android.core_dependent.ext.collectFlowWhenStarted
import com.mument_android.core_dependent.util.ViewUtils.showToast
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.home.R
import com.mument_android.home.adapters.NotifyAdapter
import com.mument_android.home.databinding.ActivityNotifyBinding
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.*
import com.mument_android.home.util.NotifyType
import com.mument_android.home.viewmodels.NotifyViewModel

class NotifyActivity : BaseActivity<ActivityNotifyBinding>(R.layout.activity_notify) {

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
    }

    private fun moveToNoticeView(notify: Notify) {
        //Move Notice
    }

    private fun adapterSetting() {
        notifyAdapter = NotifyAdapter { notifyViewModel.emitEvent(NotifyEvent.OnClickNotify(it)) }
        binding.rvNotifyList.adapter = notifyAdapter
        notifyAdapter.submitList(
            listOf(
                Notify(
                    "ajadsk lf;jasjlfkhasl jksfdjsagfahjgdsa jhfgsdasdsa daskldjalk djalksajdlka sjdklsajdklasjdkl 가난다나ㅏㄴ암나이니asjdkl가sjdklsajdklasjdkl가sjdklsajdklasjdkl가sjdklsajdklasjdkl가".replace(" ".toRegex(), "\u00A0"), //단어가 길면 자동 줄바꿈이 일어나므로 유니코드 변경, 나중에 전반적으로 수정할 예정
                    Music("sad", "asdsad", "sadsadas", "image"),
                    NotifyType.LIKE
                ),
                Notify(
                    "ajadsklf;jasjlfkh",
                    Music("sad", "asdsad", "sadsadas", "image"),
                    NotifyType.NOTICE
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