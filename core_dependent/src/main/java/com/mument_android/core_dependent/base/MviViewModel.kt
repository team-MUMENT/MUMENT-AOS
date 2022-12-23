package com.mument_android.core_dependent.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import com.mument_android.core_dependent.ext.collectFlow
import com.mument_android.core_dependent.util.emitEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviViewModel<E: Event, S: ViewState, SE: SideEffect>: ViewModel() {
    abstract fun setInitialState(): S
    abstract fun handleEvents(event: E)

    private val _viewState = MutableStateFlow(this.setInitialState())
    val viewState: StateFlow<S> = _viewState.asStateFlow()

    private val event: MutableSharedFlow<E> = MutableSharedFlow<E>()

    private val _effect: Channel<SE> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        collectEvents()
    }

    protected fun setState(reducer: S.() -> S) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> SE) {
        viewModelScope.launch {
            _effect.send(builder())
        }
    }

    private fun collectEvents() {
        viewModelScope.launch {
            event.collect { handleEvents(it) }
        }
    }
}