package com.mument_android.core_dependent.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mument_android.core.util.Event
import com.mument_android.core.util.SideEffect
import com.mument_android.core.util.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviViewModel<E: Event, S: ViewState, SE: SideEffect>: ViewModel() {
    abstract fun setInitialState(): S
    abstract fun handleEvents(event: E)

    private val _viewState = MutableStateFlow(this.setInitialState())
    val viewState: StateFlow<S> = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<E> = MutableSharedFlow<E>()
    private val _eventDelay: MutableSharedFlow<E> = MutableSharedFlow<E>()

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

    fun emitEvent(event: E) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    fun emitDelayEvent(event: E) {
        viewModelScope.launch {
            delay(1200)
            _event.emit(event)
        }
    }

    private fun collectEvents() {
        viewModelScope.launch {
            _event.collect { handleEvents(it) }
        }
    }
}