package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.collectEvent
import com.mument_android.core_dependent.util.emitEffect
import com.mument_android.core_dependent.util.emitEvent
import com.mument_android.core_dependent.util.setState
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.*
import com.mument_android.home.util.NotifyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor() : ViewModel() {
    private val _notifyViewState: MutableStateFlow<NotifyViewState> =
        MutableStateFlow(NotifyViewState())
    val notifyViewState get() = _notifyViewState.asStateFlow()
    private val _notifyEvent: MutableSharedFlow<NotifyEvent> = MutableSharedFlow()
    private val _notifyEffect: Channel<NotifySideEffect> = Channel()
    val notifyEffect get() = _notifyEffect.receiveAsFlow()

    init {
        collectEvent()
        viewModelScope.launch {
            //Run Use Case
        }
    }

    private fun collectEvent() {
        _notifyEvent.asSharedFlow().collectEvent(viewModelScope) { event ->
            when (event) {
                NotifyEvent.OnClickBackBtn -> emitEffect(NotifySideEffect.PopBackStack)
                NotifyEvent.OnClickSettingBtn -> emitEffect(NotifySideEffect.NavToSetting)
                is NotifyEvent.OnClickNotify -> {
                    when (event.notify.notifyType) {
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
    }

    fun emitEvent(event: NotifyEvent) {
        _notifyEvent.emitEvent(viewModelScope, event)
    }

    private fun emitEffect(effect: NotifySideEffect) {
        _notifyEffect.emitEffect(viewModelScope) { effect }
    }
}