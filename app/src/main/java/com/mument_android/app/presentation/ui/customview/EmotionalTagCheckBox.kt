package com.mument_android.app.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.emoji.widget.EmojiTextView
import com.mument_android.R
import com.mument_android.app.util.ViewUtils.dpToPx
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EmotionalTagCheckBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
): EmojiTextView(context, attributeSet) {
    private val _isChecked = MutableStateFlow<Boolean>(false)
    val isChecked: StateFlow<Boolean> = _isChecked

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        setPadding(7.dpToPx(context), 5.dpToPx(context), 7.dpToPx(context), 5.dpToPx(context))
        changeCheckTheme(isChecked.value)

        setOnClickListener {
            changeIsChecked(!isChecked.value)
            changeCheckTheme(isChecked.value)
        }
    }

    fun changeIsChecked(checked: Boolean) {
        _isChecked.value = checked
    }

    private fun changeCheckTheme(isChecked: Boolean) {
        background = ContextCompat.getDrawable(context, if (isChecked) R.drawable.rectangle_fill_blue3_20dp else R.drawable.rectangle_fill_gray5_20dp)
        setTextColor(ContextCompat.getColor(context, if(isChecked) R.color.mument_color_blue1 else  R.color.mument_color_gray1))
        typeface = ResourcesCompat.getFont(context, if(isChecked) R.font.notosans_bold else R.font.notosans_medium)
    }
}