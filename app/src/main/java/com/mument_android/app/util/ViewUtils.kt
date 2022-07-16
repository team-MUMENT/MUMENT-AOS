package com.mument_android.app.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import android.util.DisplayMetrics

object ViewUtils {
    fun Int.dpToPx(context: Context): Int {
        return context.resources.displayMetrics.density.let { density ->
            (this * density).toInt()
        }
    }

    fun Int.pxToDp(context: Context): Int {
        return context.resources.displayMetrics.density.let { density ->
            (this / density).toInt()
        }
    }

    fun Context.showToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    fun Activity.getDeviceSize(): List<Int> {
        var deviceWidth = 0
        var deviceHeight = 0
        val outMetrics = DisplayMetrics()
        //defaultDisplay Deprecated로 인한 Version 처리

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = this.display
            display?.getRealMetrics(outMetrics)
            deviceHeight = outMetrics.heightPixels
            deviceWidth = outMetrics.widthPixels
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
            deviceHeight = outMetrics.heightPixels
            deviceWidth = outMetrics.widthPixels
        }
        return listOf(deviceWidth, deviceHeight)
    }
}