package com.mument_android.core_dependent.ext

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.mument_android.core.model.TagEntity.Companion.TAG_IS_FIRST
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.core_dependent.R

inline fun View.click(crossinline block: () -> Unit) {
    setOnClickListener { block() }
}

fun TextView.changeTextColor(id: Int) {
    setTextColor(context.getColor(id))
}
object BindingExtension{

@JvmStatic
@BindingAdapter("setTagType")
fun AppCompatTextView.setTagType(tagType :String){

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
}