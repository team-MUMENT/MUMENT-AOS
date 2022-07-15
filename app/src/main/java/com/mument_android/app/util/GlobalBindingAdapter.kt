package com.mument_android.app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

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

//    @JvmStatic
//    @BindingAdapter("selectedTag")
//    fun EmotionalTagCheckBox.setIsChecked(isSelected: Boolean) = changeIsChecked(isSelected)
//
//    @JvmStatic
//    @BindingAdapter("checkedChanged")
//    fun EmotionalTagCheckBox.setListener(listener: InverseBindingListener) {
//        findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenStarted {
//            isChecked.collect { listener.onChange() }
//        }
//    }
//
//    @JvmStatic
//    @InverseBindingAdapter(attribute = "selectedTag", event = "checkedChanged")
//    fun EmotionalTagCheckBox.getIsChecked(): Boolean? = isChecked.value

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

//    데이터 바인딩을 써보고 싶은 진실의 발악,,담에 배우고 수정할게요,,
//    @JvmStatic
//    @BindingAdapter("buttonClick")
//    fun buttonClick(button: Button, click: Boolean) {
//        button.isSelected = click
//    }
}