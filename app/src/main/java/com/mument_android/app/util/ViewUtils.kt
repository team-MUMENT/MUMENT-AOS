package com.mument_android.app.util

import android.content.Context
import android.widget.Toast

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
    }
}