package com.mument_android.app.util

import android.app.Activity
import android.util.DisplayMetrics
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

fun <T>StateFlow<T>.launchWhenCreated(lifecycleScope: LifecycleCoroutineScope, callback: (T) -> Unit) {
    lifecycleScope.launchWhenCreated { collect { callback(it) } }
}

fun <T>StateFlow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope, callback: (T) -> Unit) {
    lifecycleScope.launchWhenStarted { collect { callback(it) } }
}

fun <T>StateFlow<T>.launchWhenResumed(lifecycleScope: LifecycleCoroutineScope, callback: (T) -> Unit) {
    lifecycleScope.launchWhenResumed { collect { callback(it) } }
}



