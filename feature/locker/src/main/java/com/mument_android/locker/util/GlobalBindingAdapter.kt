package com.mument_android.locker.util

import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.mument_android.core.model.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.locker.R


object GlobalBindingAdapter {
    const val EMPTY_PROFILE = "https://mument.s3.ap-northeast-2.amazonaws.com/user/emptyImage.jpg"


    @JvmStatic
    @BindingAdapter("setMovementMethod")
    fun TextView.setMovementMethod(scroll: Boolean) {
        if (scroll) movementMethod = ScrollingMovementMethod()
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
    @BindingAdapter("load_profile")
    fun loadProfileImage(view: ImageView, url: String?) {
        val profileImage = if (!url.isNullOrEmpty()) url else EMPTY_PROFILE
        view.load(profileImage) {
            crossfade(true)
            this.transformations(CircleCropTransformation())
        }
    }

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
    @BindingAdapter("setTagType")
    fun AppCompatTextView.setTagType(tagType: String) {
        val backgroundDrawable =
            if (tagType == TAG_IS_FIRST) R.drawable.rectangle_fill_purple2_20dp else R.drawable.rectangle_fill_gray5_20dp
        val textColor =
            if (tagType == TAG_IS_FIRST) R.color.mument_color_purple1 else R.color.mument_color_gray1
        background = ContextCompat.getDrawable(context, backgroundDrawable)
        setTextColor(ContextCompat.getColor(context, textColor))
        typeface = ResourcesCompat.getFont(context, R.font.notosans_medium)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        setPadding(7.dpToPx(context), 5.dpToPx(context), 7.dpToPx(context), 5.dpToPx(context))
    }

    @JvmStatic
    @BindingAdapter("setSelectTagType")
    fun AppCompatTextView.setSelectTagType(tagType: String) {
        val backgroundDrawable = R.drawable.rectangle_fill_blue3_20dp
        val textColor = R.color.mument_color_blue1
        background = ContextCompat.getDrawable(context, backgroundDrawable)
        setTextColor(ContextCompat.getColor(context, textColor))
        typeface = ResourcesCompat.getFont(context, R.font.notosans_medium)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        setPadding(7.dpToPx(context), 5.dpToPx(context), 7.dpToPx(context), 5.dpToPx(context))
    }
}