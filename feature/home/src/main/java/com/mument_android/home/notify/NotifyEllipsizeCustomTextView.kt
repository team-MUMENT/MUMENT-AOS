package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mument_android.home.R
import com.mument_android.home.util.SpanInfo
import com.mument_android.home.util.TextStyleCustomHelper

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private val suffix1: String = "예진"
    private val suffix2: String = "POPPOPOPOPOPOPOPOPOPOPOPOPOP"
    private val suffix: String = "에 쓴 뮤멘트를 좋아합니다."//No-break space Symbol 적용 못함,,, Span 풀림,,
    private lateinit var textStyleCustomHelper: TextStyleCustomHelper
    var isApplySpan = 0
    override fun draw(canvas: Canvas?) {
        if (text != null) {
            textStyleCustomHelper = TextStyleCustomHelper(text)
            val builder =
                textStyleCustomHelper.setEllipsize(suffix, layout) ?: SpannableStringBuilder(text)
            if (isApplySpan < 2) {   //text = builder 이후 한 번 더 불려야함
                if(textStyleCustomHelper.applySuffixesToSpans(
                    builder,
                    SpanInfo(ForegroundColorSpan(resources.getColor(R.color.mument_color_blue1)), suffix1),
                    SpanInfo(StyleSpan(Typeface.BOLD), suffix2)
                )){
                    text = builder
                    isApplySpan++
                }
            }
        } else {
            isApplySpan = 0
        }
        super.draw(canvas)
    }
}