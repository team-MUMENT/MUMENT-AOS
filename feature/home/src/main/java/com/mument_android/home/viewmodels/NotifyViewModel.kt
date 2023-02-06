package com.mument_android.home.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.network.ApiStatus
import com.mument_android.core_dependent.util.collectEvent
import com.mument_android.core_dependent.util.emitEffect
import com.mument_android.core_dependent.util.emitEvent
import com.mument_android.core_dependent.util.setState
import com.mument_android.domain.usecase.notify.FetchNotifyListDeleteUseCase
import com.mument_android.domain.usecase.notify.FetchNotifyListUseCase
import com.mument_android.domain.usecase.notify.FetchNotifyListsReadUseCase
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.*
import com.mument_android.home.notify.NotifyItemMapper
import com.mument_android.home.util.NotifyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val fetchNotifyListsReadUseCase: FetchNotifyListsReadUseCase,
    private val fetchNotifyListUseCase: FetchNotifyListUseCase,
    private val fetchNotifyListDeleteUseCase: FetchNotifyListDeleteUseCase,
    private val notifyItemMapper: NotifyItemMapper
) : ViewModel() {
    private val _notifyViewState: MutableStateFlow<NotifyViewState> =
        MutableStateFlow(NotifyViewState())
    val notifyViewState get() = _notifyViewState.asStateFlow()
    private val _notifyEvent: MutableSharedFlow<NotifyEvent> = MutableSharedFlow()
    private val _notifyEffect: Channel<NotifySideEffect> = Channel()
    val notifyEffect get() = _notifyEffect.receiveAsFlow()

    init {
        collectEvent()
        viewModelScope.launch {
            fetchNotifyListUseCase.invoke().map { status ->
                when (status) {
                    is ApiStatus.Success -> {
                        status.data?.let { notifyItemMapper.map(it) }
                    }
                    else -> {
                        null
                    }
                }
            }.collect {
                Log.e("REsult!!", it.toString())
                _notifyViewState.setState { copy(notifyList = it) }
            }
            //Run Use Case
            /*_notifyViewState.setState {
                copy(
                    notifyList = listOf(
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
                            userId = 2,
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
                            userId = 3,
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
                            userId = 4,
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
                            userId = 5,
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
                            userId = 6,
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
                            userId = 7,
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
                            userId = 8,
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
                            userId = 9,
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
                            userId = 10,
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
                            userId = 11,
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
                            userId = 12,
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
                            userId = 13,
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
                            userId = 14,
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
                            userId = 15,
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
                            userId = 16,
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
                            userId = 17,
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
                            userId = 18,
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
                            userId = 19,
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
                            userId = 20,
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
                            userId = 21,
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
                            userId = 22,
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
                            userId = 23,
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
                            userId = 24,
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
                            userId = 25,
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
                            userId = 26,
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
                            userId = 27,
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
                            userId = 28,
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
                            userId = 29,
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
                            userId = 30,
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
                            userId = 31,
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
                            userId = 32,
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
            }*/
        }
    }

    private fun collectEvent() {
        _notifyEvent.asSharedFlow().collectEvent(viewModelScope) { event ->
            when (event) {
                NotifyEvent.OnClickBackBtn -> emitEffect(NotifySideEffect.PopBackStack)
                NotifyEvent.OnClickSettingBtn -> emitEffect(NotifySideEffect.NavToSetting)
                is NotifyEvent.OnClickNotify -> {
                    when (event.notify.type) {
                        NotifyType.LIKE -> {
                            emitEffect(NotifySideEffect.NavToMumentDetail(event.notify))
                        }
                        NotifyType.NOTICE -> {
                            emitEffect(NotifySideEffect.NavToNotice(event.notify))
                        }
                        NotifyType.MARKETING -> {
                            //Nav to Some View
                        }
                    }
                }
                is NotifyEvent.OnDeleteNotify -> {
                    emitEffect(NotifySideEffect.DeleteNotify(event.notify))
                }
            }
        }
    }

    fun deleteNotify(notify: Notify) {
        val current = _notifyViewState.value.notifyList?.toMutableList()
        if (current?.remove(notify) == true) {      //Modify Remote call response OK
            _notifyViewState.setState { copy(notifyList = current) }
        }
        /*viewModelScope.launch {
            fetchNotifyListDeleteUseCase.invoke(notify.id)
        }*/
    }

    fun emitEvent(event: NotifyEvent) {
        _notifyEvent.emitEvent(viewModelScope, event)
    }

    private fun emitEffect(effect: NotifySideEffect) {
        _notifyEffect.emitEffect(viewModelScope) { effect }
    }
}