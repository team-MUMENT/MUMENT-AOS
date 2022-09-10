package com.mument_android.app.util

import android.view.View
import android.widget.TextView

inline fun View.click(crossinline block: () -> Unit) {
    setOnClickListener { block() }
}

fun TextView.changeTextColor(id: Int) {
    setTextColor(context.getColor(id))
}