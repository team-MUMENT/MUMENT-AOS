package com.mument_android.app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.mument_android.app.presentation.ui.customview.EmotionalTagCheckBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object GlobalBindingAdapter {

    @JvmStatic
    @BindingAdapter("load_profile")
    fun loadProfileImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            view.load(url) {
                crossfade(true)
                this.transformations(CircleCropTransformation())
            }
        } else {
        }
    }

    @JvmStatic
    @BindingAdapter("load_album")
    fun loadAlbumImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            view.load(url) {
                crossfade(true)
                this.transformations(RoundedCornersTransformation(11.0f))
            }
        } else {
        }
    }

    @JvmStatic
    @BindingAdapter("selectedTag")
    fun EmotionalTagCheckBox.setIsChecked(isSelected: Boolean) = changeIsChecked(isSelected)

    @JvmStatic
    @BindingAdapter("checkedChanged")
    fun EmotionalTagCheckBox.setListener(listener: InverseBindingListener) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenStarted {
            isChecked.collect { listener.onChange() }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedTag", event = "checkedChanged")
    fun EmotionalTagCheckBox.getIsChecked(): Boolean? = isChecked.value

    @JvmStatic
    @BindingAdapter("load_album_top")
    fun loadAlbumTopImage(view: ImageView, url: String) {
        if (url.isNotBlank()) {
            view.load(url) {
                crossfade(true)
                this.transformations(RoundedCornersTransformation(11.0f, 11.0f))
            }
        } else {
            TODO("placeholder")
        }
    }
}