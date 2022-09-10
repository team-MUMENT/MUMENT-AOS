package com.mument_android.app.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T>StateFlow<T>.launchWhenCreated(lifecycleScope: LifecycleCoroutineScope, crossinline callback: (T) -> Unit) {
    lifecycleScope.launchWhenCreated { collect { callback(it) } }
}

inline fun <T, R> R.collectFlow(
    flow: Flow<T>, state: Lifecycle.State, crossinline block: suspend (T) -> Unit
) {
    when(this) {
        is AppCompatActivity -> {
            lifecycleScope.launch {
                flow.flowWithLifecycle(lifecycle, state).collect { block(it) }
            }
        }
        is Fragment -> {
            viewLifecycleOwner.lifecycleScope.launch {
                flow.flowWithLifecycle(viewLifecycleOwner.lifecycle, state).collect { block(it) }
            }
        }
        else -> {}
    }
}

inline fun <T, R> R.collectFlowWhenStarted(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) = collectFlow(flow, Lifecycle.State.STARTED, block)

inline fun <T, R> R.collectFlowWhenCreated(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) = collectFlow(flow, Lifecycle.State.CREATED, block)