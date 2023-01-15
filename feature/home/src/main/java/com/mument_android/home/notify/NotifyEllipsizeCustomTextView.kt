package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ComplexColorCompat
import com.mument_android.home.R
import com.mument_android.home.util.SpanInfo
import com.mument_android.home.util.TextStyleCustomHelper

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private lateinit var textStyleCustomHelper: TextStyleCustomHelper
    private var isApplySpanLimit = 0
    fun clearSpanCount(){
        isApplySpanLimit = 0
    }
    override fun draw(canvas: Canvas?) {
        if (text != null) {
            textStyleCustomHelper = TextStyleCustomHelper(text)
            val builder =
                textStyleCustomHelper.setEllipsizeMusic(layout = layout) ?: SpannableStringBuilder(
                    text
                )
            if (isApplySpanLimit < 2) {   //text = builder 이후 한 번 더 불려야함
                if (textStyleCustomHelper.applyNotifySpan(text = builder, nameColor = context.getColor(R.color.mument_color_blue1))) {
                    text = builder
                    isApplySpanLimit++
                }
            }
        } else {
            isApplySpanLimit = 0
        }
        super.draw(canvas)
    }
}