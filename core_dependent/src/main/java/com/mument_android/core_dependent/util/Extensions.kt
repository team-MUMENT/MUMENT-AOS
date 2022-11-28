package com.mument_android.core_dependent.util

import androidx.lifecycle.viewModelScope
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.UserEvent
import com.mument_android.core.util.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <S: ViewState> MutableStateFlow<S>.setState(reducer: S.() -> S) {
    val newState = this.value.reducer()
    value = newState
}

inline fun <E: SideEffect> Channel<E>.setEffect(coroutineScope: CoroutineScope, crossinline builder: () -> E) {
    coroutineScope.launch { send(builder()) }
}

fun <UE: UserEvent> MutableSharedFlow<UE>.emitUserEvent(coroutineScope: CoroutineScope, event: UE) {
    coroutineScope.launch { emit(event) }
}

inline fun <UE: UserEvent> SharedFlow<UE>.handleUserEvent(coroutineScope: CoroutineScope, crossinline receiver: (UE) -> Unit) {
    coroutineScope.launch {
        collect { receiver(it) }
    }
}