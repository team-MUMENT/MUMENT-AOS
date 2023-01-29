package com.mument_android.home.notify

import android.content.Intent
import android.os.Bundle
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
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.LIKE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = null,
                    noticeTitle = null,
                    likeMusicTitle = "POPPOPOPOPOPOPOP POPOPOPOPOPPOPP OPOPOPOPOPOPPOPO POPOPOPOPOPOPOPOPOPOPOPOP".replace(
                        " ",
                        "\u00A0"
                    ),
                    likeProfileId = "예진",
                ),
                Notify(
                    id = "",
                    type = NotifyType.NOTICE,
                    userId = 1,
                    isDeleted = true,
                    isRead = true,
                    createdAt = "02/05 09:10",
                    linkId = 1,
                    noticePoint = "1.1.1",
                    noticeTitle = "버전 업데이트 공지사항입니다.",
                    likeMusicTitle = null,
                    likeProfileId = null,
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