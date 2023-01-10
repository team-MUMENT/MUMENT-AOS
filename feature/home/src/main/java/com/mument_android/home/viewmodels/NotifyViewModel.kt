package com.mument_android.home.viewmodels

import androidx.lifecycle.ViewModel
import com.mument_android.home.notify.NotifyContract.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class NotifyViewModel:ViewModel() {
    private val _notifyEvent: MutableSharedFlow<NotifyEvent> = MutableSharedFlow()
    private val _notifyEffect: Channel<NotifySideEffect> = Channel()
    val notifyEffect get() = _notifyEffect.receiveAsFlow()

}