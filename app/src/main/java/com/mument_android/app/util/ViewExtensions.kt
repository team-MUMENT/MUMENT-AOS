package com.mument_android.app.util

import android.view.View

inline fun View.click(crossinline block: () -> Unit) {
    setOnClickListener { block() }
}