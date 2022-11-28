package com.mument_android.core_dependent.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.mument_android.core_dependent.R
import com.mument_android.core_dependent.util.ViewUtils.dpToPx


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

    fun View.applyVisibilityAnimation(isUpward: Boolean, reveal: Boolean, durationTime: Long, delay: Long? = null) {
        val animationSet = AnimationSet(true)
        val alphaAnimation = if (reveal) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
        val translateAnimation = when {
            isUpward && reveal -> TranslateAnimation(0f, 0f, 70f, 0f)
            isUpward && !reveal -> TranslateAnimation(0f, 0f, 0f, -height.toFloat())
            !isUpward && reveal -> TranslateAnimation(0f, 0f,  -height.toFloat(),0f)
            else -> TranslateAnimation(0f, 0f,  0f, height.toFloat())
        }

        alphaAnimation.duration = durationTime
        translateAnimation.duration = durationTime
        delay?.let {
            alphaAnimation.startOffset = it
            translateAnimation.startOffset = it
        }
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(translateAnimation)

        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                if (reveal) {visibility = View.VISIBLE}
            }
            override fun onAnimationEnd(p0: Animation?) {
                visibility = if(reveal) View.VISIBLE else View.GONE
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        startAnimation(animationSet)
    }
}

fun ViewGroup.showProgress() {
    val size = 50.dpToPx(context)
    if (!children.any { it is ProgressBar}) {
        viewTreeObserver.addOnGlobalLayoutListener(object: OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ProgressBar(context)
                    .also {
                        it.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also {
                            it.width = size
                            it.height = size
                        }
                        it.x = (width - size).toFloat() / 2
                        it.y = (height - size).toFloat() / 2
                    }.also { addView(it) }
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}

fun ViewGroup.removeProgress() {
    val progress = children.find { it is ProgressBar }
    removeView(progress)
}
