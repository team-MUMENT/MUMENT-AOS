package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core_dependent.util.collectEvent
import com.mument_android.core_dependent.util.emitEffect
import com.mument_android.core_dependent.util.emitEvent
import com.mument_android.home.models.Notify
import com.mument_android.home.notify.NotifyContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor() : ViewModel() {
    private val _notifyEvent: MutableSharedFlow<NotifyEvent> = MutableSharedFlow()
    private val _notifyEffect: Channel<NotifySideEffect> = Channel()
    val notifyEffect get() = _notifyEffect.receiveAsFlow()
    private val _notifyViewState: MutableStateFlow<NotifyViewState> = MutableStateFlow(
        NotifyViewState()
    )
    val notifyViewState get() = _notifyViewState.asStateFlow()

    init {
        collectEvent()
        viewModelScope.launch {

        }
    }

    private fun collectEvent() {
        _notifyEvent.asSharedFlow().collectEvent(viewModelScope) { event ->
            when (event) {
                NotifyEvent.OnClickBackBtn -> emitEffect(NotifySideEffect.PopBackStack)
                NotifyEvent.OnClickSettingBtn -> emitEffect(NotifySideEffect.NavToSetting)
                is NotifyEvent.OnClickNotify -> {
                    emitEffect(NotifySideEffect.NavToMumentDetail(event.notify))
                }
                is NotifyEvent.OnDeleteNotify -> {
                    emitEffect(NotifySideEffect.DeleteNotify(event.notify))
                }
            }
        }
    }
    fun deleteNotify(notify: Notify){

    }

    fun emitEvent(event: NotifyEvent) {
        _notifyEvent.emitEvent(viewModelScope, event)
    }

    private fun emitEffect(effect: NotifySideEffect) {
        _notifyEffect.emitEffect(viewModelScope) { effect }
    }

}