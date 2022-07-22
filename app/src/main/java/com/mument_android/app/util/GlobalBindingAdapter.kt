package com.mument_android.app.util

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.ImageSpan
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mument_android.R
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.app.util.ViewUtils.dpToPx

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
    @BindingAdapter("setMovementMethod")
    fun TextView.setMovementMethod(scroll: Boolean) {
        if (scroll) movementMethod = ScrollingMovementMethod()
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

    @BindingAdapter("isLike")
    fun setLike(imageView: ImageView, option : Boolean) {
        imageView.isSelected = option
    }

    @JvmStatic
    @BindingAdapter("isPrivate")
    fun isPrivate(imageView: ImageView, option: Boolean) {
        if(!option){
            imageView.visibility = View.GONE
        }else{
            imageView.visibility = View.VISIBLE
        }
    }

}