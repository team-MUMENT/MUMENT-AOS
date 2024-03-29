package com.mument_android.core_dependent.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
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
        toast.show()
    }

    fun Context.snackBar(view: View, msg: String) {
        val snack = Snackbar.make(view, msg, 2000)

        val snackBarView = snack.view
        val snackBarText =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarText.textAlignment = View.TEXT_ALIGNMENT_CENTER // 안내 텍스트 위치 조정
        snackBarText.textSize = 12.0F
        snackBarText.typeface = ResourcesCompat.getFont(
            this, R.font.notosans_medium
        )
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

    fun View.applyVisibilityAnimation(
        isUpward: Boolean,
        reveal: Boolean,
        durationTime: Long,
        delay: Long? = null
    ) {
        val animationSet = AnimationSet(true)
        val alphaAnimation = if (reveal) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
        val translateAnimation = when {
            isUpward && reveal -> TranslateAnimation(0f, 0f, 70f, 0f)
            isUpward && !reveal -> TranslateAnimation(0f, 0f, 0f, -height.toFloat())
            !isUpward && reveal -> TranslateAnimation(0f, 0f, -height.toFloat(), 0f)
            else -> TranslateAnimation(0f, 0f, 0f, height.toFloat())
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
                if (reveal) {
                    visibility = View.VISIBLE
                }
            }

            override fun onAnimationEnd(p0: Animation?) {
                visibility = if (reveal) View.VISIBLE else View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
        startAnimation(animationSet)
    }

    fun View.hideKeyboard() {
        val inputManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun ViewGroup.showProgress() {
    val size = 50.dpToPx(context)
    if (!children.any { it is ProgressBar }) {
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ProgressBar(context)
                    .also {
                        it.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).also {
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

inline fun <T : View> T.checkIsViewLoaded(crossinline callback: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback()
            }
        }
    })
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra<T>(key)
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable<T>(key)
}


fun Context.createSpannableString(
    originalText: String,
    coloredText: String
): SpannableString {
    val spannableString = SpannableString(originalText)
    val start = originalText.indexOf(coloredText)
    val end = start + coloredText.length
    val color =
        if (start != -1) ContextCompat.getColor(this, R.color.mument_color_black2) else 0
    spannableString.setSpan(
        ForegroundColorSpan(color),
        start - 1,
        end + 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}