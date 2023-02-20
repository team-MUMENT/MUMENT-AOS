package com.mument_android.locker.util

import android.content.res.Resources
import android.util.TypedValue

class PixelRatio {
    private val displayMetrics
        get() = Resources.getSystem().displayMetrics

    fun dpToPxF(dp: Int): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)

}
val Number.dpToPxF: Float
    get() = PixelRatio().dpToPxF(this.toInt())