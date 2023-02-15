package com.mument_android.record.util

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mument_android.core_dependent.util.createSpannableString
import com.mument_android.record.R
import com.mument_android.record.RecordActivity

object GlobalBindingAdapter {
    const val EMPTY_PROFILE = "https://mument.s3.ap-northeast-2.amazonaws.com/user/emptyImage.jpg"

    @JvmStatic
    @BindingAdapter("load_album_five")
    fun loadAlbumFiveImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            view.load(url) {
                crossfade(true)
                this.transformations(RoundedCornersTransformation(5.0f))
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

@BindingAdapter("app:coloredText")
fun TextView.setColoredText(coloredText: String) {
    if (coloredText.isNotBlank()) {
        val originalText = text.toString()
        val spannableString = (context as Context).createSpannableString(originalText, coloredText)
        text = spannableString
    }
}