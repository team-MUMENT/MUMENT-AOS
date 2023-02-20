package com.mument_android.core_dependent.util

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(private val interval: Int = 1000, val onSingleClick: (View) -> Unit) :
    View.OnClickListener {

    private var lastClickTime: Long = 0
    override fun onClick(view: View) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if ((elapsedRealtime - lastClickTime) < interval) {
            return
        }
        lastClickTime = elapsedRealtime
        onSingleClick(view)
    }
}