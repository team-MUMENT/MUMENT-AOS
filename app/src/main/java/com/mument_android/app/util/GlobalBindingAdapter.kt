package com.mument_android.app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object GlobalBindingAdapter {



    @JvmStatic
    @BindingAdapter("load_profile")
    fun loadProfileImage(view: ImageView, url: String) {
        if (url.isNotBlank()) {
            CoroutineScope(Dispatchers.Main).launch {
                view.load(url) {
                    crossfade(true)
                    this.transformations(CircleCropTransformation())
                }
            }
        } else {
            TODO("placeholder")
        }
    }
    @JvmStatic
    @BindingAdapter("load_album")
    fun loadAlbumImage(view: ImageView, url: String) {
        if (url.isNotBlank()) {
            CoroutineScope(Dispatchers.Main).launch {
                view.load(url) {
                    crossfade(true)
                    this.transformations(RoundedCornersTransformation(11.0f))
                }
            }
        } else {
            TODO("placeholder")
        }
    }
}