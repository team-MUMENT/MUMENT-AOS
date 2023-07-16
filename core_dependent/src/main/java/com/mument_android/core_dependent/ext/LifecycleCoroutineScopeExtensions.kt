package com.mument_android.core_dependent.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*

inline fun <T, R : LifecycleOwner> R.collectFlow(
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

inline fun <T, R : LifecycleOwner> R.collectFlowWhenStarted(
    flow: Flow<T>, crossinline block: suspend (T) -> Unit
) = collectFlow(flow, block) // Lifecycle.State.STARTED는 Default로 들어감
