package com.mument_android.mypage.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

object BindingAdapter {
    const val EMPTY_PROFILE = "https://mument.s3.ap-northeast-2.amazonaws.com/user/emptyImage.jpg"

    @JvmStatic
    @BindingAdapter("load_profile")
    fun loadProfileImage(view: ImageView, url: String?) {
        val profileImage = if (!url.isNullOrEmpty()) url else EMPTY_PROFILE
        view.load(profileImage) {
            crossfade(true)
            this.transformations(CircleCropTransformation())
            this.size(60)
        }
    }
}