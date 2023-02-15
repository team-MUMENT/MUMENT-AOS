package com.mument_android.mypage.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("load_profile")
    fun loadProfileImage(view: ImageView, url: String?) {
        view.load(url) {
            crossfade(true)
            this.transformations(CircleCropTransformation())
        }
    }
}