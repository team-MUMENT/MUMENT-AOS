package com.mument_android.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

object GlobalBindingAdapter {
    const val EMPTY_PROFILE = "https://mument.s3.ap-northeast-2.amazonaws.com/user/emptyImage.jpg"

    @JvmStatic
    @BindingAdapter("load_profile")
    fun loadProfileImage(view: ImageView, url: String?) {
        val profileImage = if (!url.isNullOrEmpty()) url else EMPTY_PROFILE
        view.load(profileImage) {
            crossfade(true)
            this.transformations(CircleCropTransformation())
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
    @BindingAdapter("load_album_back")
    fun loadAlbumImage(view: ImageView, url: Int?) {
        if (url != null) {
            view.load(url) {
                crossfade(true)
                this.transformations(RoundedCornersTransformation(34.0f))
            }
        } else {
        }
    }

//    @JvmStatic
//    @BindingAdapter("android:text")
//    fun EmotionalTagCheckBox.setText(text: String) {
//        getEmojiTextViewHelper()
//        setText(text)
//    }
//
//    @JvmStatic
//    @BindingAdapter("textChanged")
//    fun EmotionalTagCheckBox.setTextChangedListener(listener: InverseBindingListener) {
//        addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun afterTextChanged(p0: Editable?) { listener.onChange() }
//        })
//    }
//
//    @JvmStatic
//    @InverseBindingAdapter(attribute = "android:text", event = "textChanged")
//    fun EmotionalTagCheckBox.getText(): String? {
//        getEmojiTextViewHelper()
//        return text.toString()
//    }

    @JvmStatic
    @BindingAdapter("load_album_top")
    fun loadAlbumTopImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            view.load(url) {
                crossfade(true)
                this.transformations(RoundedCornersTransformation(11.0f, 11.0f))
            }
        } else {
        }
    }

    @JvmStatic
    @BindingAdapter("load_search_album")
    fun loadSearchImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            view.load(url) {
                crossfade(true)
            }
        } else {
        }
    }


    @JvmStatic
    @BindingAdapter("emptyView")
    fun setEmptyView(view: ImageView, option: Int) {
        when (option) {
            1 -> {
                view.setImageResource(R.drawable.empty_music)
            }
            2 -> {
                view.setImageResource(R.drawable.empty_mument)
            }
            3 -> {
                view.setImageResource(R.drawable.ic_alert)
            }
        }
    }

}