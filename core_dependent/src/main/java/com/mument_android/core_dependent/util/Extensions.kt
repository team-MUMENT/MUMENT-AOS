package com.mument_android.core_dependent.util

import android.text.TextUtils
import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

fun <S : ViewState> MutableStateFlow<S>.setState(reducer: S.() -> S) {
    val newState = this.value.reducer()
    value = newState
}

inline fun <E : SideEffect> Channel<E>.emitEffect(
    coroutineScope: CoroutineScope,
    crossinline builder: () -> E
) {
    coroutineScope.launch { send(builder()) }
}

fun <E : Event> MutableSharedFlow<E>.emitEvent(coroutineScope: CoroutineScope, event: E) {
    coroutineScope.launch { emit(event) }
}

inline fun <E : Event> SharedFlow<E>.collectEvent(
    coroutineScope: CoroutineScope,
    crossinline receiver: (E) -> Unit
) {
    coroutineScope.launch { collect { receiver(it) } }
}

fun CharSequence.myIsDigitsOnly(): Boolean {
    return this.isNotEmpty() && TextUtils.isDigitsOnly(this)
}