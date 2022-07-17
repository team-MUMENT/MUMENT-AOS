package com.mument_android.app.util

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import java.lang.IllegalArgumentException

fun <T>StateFlow<T>.launchWhenCreated(lifecycleScope: LifecycleCoroutineScope, callback: (T) -> Unit) {
    lifecycleScope.launchWhenCreated { collect { callback(it) } }
}

fun <T>StateFlow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope, callback: (T) -> Unit) {
    lifecycleScope.launchWhenStarted { collect { callback(it) } }
}

fun <T>StateFlow<T>.launchWhenResumed(lifecycleScope: LifecycleCoroutineScope, callback: (T) -> Unit) {
    lifecycleScope.launchWhenResumed { collect { callback(it) } }
}

