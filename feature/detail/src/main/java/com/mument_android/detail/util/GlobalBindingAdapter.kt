package com.mument_android.detail.util

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.ImageSpan
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mument_android.core.model.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.detail.R

object GlobalBindingAdapter {
    val EMPTY_PROFILE = com.mument_android.core_dependent.R.drawable.mument_default_profile

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
        with(view) {
            if (url.isNullOrEmpty()) {
                view.load(context.getDrawable(EMPTY_PROFILE)) { toCircularShape() }
            } else {
                view.load(url) { toCircularShape() }
            }
        }
    }

    private fun ImageRequest.Builder.toCircularShape() {
        crossfade(true)
        transformations(CircleCropTransformation())
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
    @BindingAdapter(value = ["stringRes", "drawableIcon"])
    fun AppCompatTextView.setDrawableLeftGravityCenter(stringRes: String, drawableIcon: Drawable) {
        text = SpannableString("   $stringRes").apply {
            drawableIcon.setBounds(0, 0, drawableIcon.intrinsicWidth, drawableIcon.intrinsicHeight)
            setSpan(
                ImageSpan(drawableIcon),
                0,
                1,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }
    @JvmStatic
    @BindingAdapter("toolbarElipseTitle")
    fun CollapsingToolbarLayout.setToolbarEllipsize(name: String?) {
        name?.let {
            title = if(it.count() > 16) {
                it.substring(0,15) + "..."
            } else {
                it
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setTextEllipse")
    fun TextView.setTextViewEllipse(name: String?) {
        name?.let {
            text = if(it.count() > 16) {
                it.substring(0,15) + "..."
            } else {
                it
            }
        }
    }
}