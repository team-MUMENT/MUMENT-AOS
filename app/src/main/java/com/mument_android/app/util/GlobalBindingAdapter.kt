package com.mument_android.app.util

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
import com.mument_android.R
import com.mument_android.app.domain.entity.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.app.util.ViewUtils.dpToPx

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

    @JvmStatic
    @BindingAdapter("setMovementMethod")
    fun TextView.setMovementMethod(scroll: Boolean) {
        movementMethod = ScrollingMovementMethod()
    }

    @JvmStatic
    @BindingAdapter("setTagType")
    fun AppCompatTextView.setTagType(tagType: String) {
        val backgroundDrawable = if (tagType == TAG_IS_FIRST) R.drawable.rectangle_fill_purple2_20dp else R.drawable.rectangle_fill_gray5_20dp
        val textColor = if (tagType == TAG_IS_FIRST) R.color.mument_color_purple1 else R.color.mument_color_gray1
        background = ContextCompat.getDrawable(context, backgroundDrawable)
        setTextColor(ContextCompat.getColor(context, textColor))
        typeface = ResourcesCompat.getFont(context, R.font.notosans_medium)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP,12f)
        setPadding(7.dpToPx(context), 5.dpToPx(context), 7.dpToPx(context), 5.dpToPx(context))
    }
}