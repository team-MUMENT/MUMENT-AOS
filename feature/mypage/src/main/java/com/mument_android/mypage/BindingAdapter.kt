package com.mument_android.mypage

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setLayoutMarginTop")
    fun setMarginTop(view: View, dimen: Float) {
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = dimen.toInt()
        }
    }
}