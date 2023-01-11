package com.mument_android.home.notify

import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import com.mument_android.home.models.Notify

class NotifyContract {

    data class NotifyViewState(
        override val hasError: Boolean = false,
        override val onNetwork: Boolean = false,
        val notifyList: List<Notify>? = null
    ) : ViewState

    sealed class NotifyEvent : Event {
        object OnClickBackBtn : NotifyEvent()
        object OnClickSettingBtn : NotifyEvent()
        data class OnClickNotify(val notify: Notify) : NotifyEvent()
        data class OnDeleteNotify(val notify: Notify) : NotifyEvent()
    }

    sealed class NotifySideEffect : SideEffect {
        object PopBackStack : NotifySideEffect()
        object NavToSetting : NotifySideEffect()
        data class Toast(val message: String) : NotifySideEffect()
        data class DeleteNotify(val notify: Notify) : NotifySideEffect()
        data class NavToMumentDetail(val notify: Notify) : NotifySideEffect()
        data class NavToNotice(val notify: Notify) : NotifySideEffect()
    }
}