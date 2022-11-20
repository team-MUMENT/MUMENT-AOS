package com.mument_android.core_dependent.util

import com.mument_android.core.util.ViewState
import kotlinx.coroutines.flow.MutableStateFlow

inline fun <S: ViewState> MutableStateFlow<S>.setState(crossinline reducer: S.() -> S) {
    val newState = this.value.reducer()
    value = newState
}