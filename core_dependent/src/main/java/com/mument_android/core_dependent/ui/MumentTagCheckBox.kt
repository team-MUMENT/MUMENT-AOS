package com.mument_android.core_dependent.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.res.ResourcesCompat
import com.mument_android.core_dependent.util.ViewUtils.dpToPx
import com.mument_android.core_dependent.R

class MumentTagCheckBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatCheckBox(context, attributeSet) {
    init {
        buttonDrawable = null
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        setPadding(7.dpToPx(context), 5.dpToPx(context), 7.dpToPx(context), 5.dpToPx(context))
        setTextColor(context.getColorStateList(R.color.selector_color_gray1_to_blue1))
        setBackgroundResource(R.drawable.selector_tag_background_fill_20dp)
        setOnCheckedChangeListener { button, isChecked ->
            typeface = ResourcesCompat.getFont(context, if (isChecked) R.font.notosans_bold else R.font.notosans_medium)
        }
    }
}