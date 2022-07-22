package com.mument_android.app.presentation.ui.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.mument_android.R
import com.mument_android.app.util.ViewUtils.dpToPx

class MumentTagCheckBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
): AppCompatCheckBox(context, attributeSet) {
    init {
        buttonDrawable = null
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        setPadding(13.dpToPx(context), 8.dpToPx(context), 13.dpToPx(context), 8.dpToPx(context))
        setTextColor(context.getColorStateList(R.color.selector_color_gray1_to_blue1))
        setBackgroundResource(R.drawable.selector_tag_background_fill_20dp)
        setOnCheckedChangeListener { button, isChecked ->
            typeface = ResourcesCompat.getFont(context, if (isChecked) R.font.notosans_bold else R.font.notosans_medium)
        }
    }
}