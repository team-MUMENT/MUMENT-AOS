package com.mument_android.app.util

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import android.util.DisplayMetrics
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.mument_android.R
import timber.log.Timber


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


    fun Context.showToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        val viewGroup = toast.view as ViewGroup
        val textView = viewGroup.getChildAt(0) as TextView
        textView.textSize = 12.0F
        textView.gravity = Gravity.CENTER_VERTICAL
        toast.show()
    }

    fun Context.snackBar(view: View, msg: String) {
        val snack = Snackbar.make(view, msg, 2000)

        val snackBarView = snack.view
        val snackBarText =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val snackBarLayout = snackBarView.layoutParams as FrameLayout.LayoutParams
        snackBarLayout.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM // 레이아웃 위치 조정
        snackBarText.textAlignment = View.TEXT_ALIGNMENT_CENTER // 안내 텍스트 위치 조정
        snackBarText.textSize = 12.0F
        snackBarText.typeface = ResourcesCompat.getFont(
            this,  R.font.notosans_medium)
        snack.show()
    }

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
