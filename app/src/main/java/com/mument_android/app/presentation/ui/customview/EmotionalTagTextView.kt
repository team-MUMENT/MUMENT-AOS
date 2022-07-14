package com.mument_android.app.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.emoji.widget.EmojiTextView
import com.mument_android.R
import com.mument_android.app.util.ViewUtils.dpToPx

class EmotionalTagTextView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int =-1
): EmojiTextView(context, attributeSet, defStyle) {
    init {
        background = ContextCompat.getDrawable(context, R.drawable.rectangle_fill_gray5_20dp)
        setTextColor(ContextCompat.getColor(context, R.color.mument_color_gray1))
        typeface = ResourcesCompat.getFont(context, R.font.notosans_medium)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        setPadding(7.dpToPx(context), 5.dpToPx(context), 7.dpToPx(context), 5.dpToPx(context))
    }
}