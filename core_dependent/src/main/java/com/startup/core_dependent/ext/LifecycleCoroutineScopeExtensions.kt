package com.startup.core_dependent.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

inline fun <T> StateFlow<T>.launchWhenCreated(
    lifecycleScope: LifecycleCoroutineScope,
    crossinline callback: (T) -> Unit
) {
    lifecycleScope.launchWhenCreated { collect { callback(it) } }
}

inline fun <T, R> R.collectFlow(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) {
    when (this) {
        is AppCompatActivity -> flow.flowWithLifecycle(lifecycle).onEach { block(it) }
            .launchIn(lifecycleScope)

        is Fragment -> flow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { block(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        else -> {}
    }
}

inline fun <T, R> R.collectFlowWhenStarted(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) = collectFlow(flow, block) // Lifecycle.State.STARTED는 Default로 들어감

inline fun <T, R> R.collectFlowWhenCreated(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) = collectFlow(flow, block)