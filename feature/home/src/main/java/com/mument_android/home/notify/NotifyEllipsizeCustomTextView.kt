package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.mument_android.home.R
import com.mument_android.home.util.TextStyleCustomHelper

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private val suffix: String = "에 쓴 뮤멘트를 좋아합니다."//No-break space Symbol 적용 못함,,, Span 풀림,,
    private lateinit var textStyleCustomHelper: TextStyleCustomHelper
    var isApplySpan = 0
    override fun draw(canvas: Canvas?) {
        if (text != null) {
            textStyleCustomHelper = TextStyleCustomHelper(text)
            val builder =
                textStyleCustomHelper.setEllipsize(suffix, layout) ?: SpannableStringBuilder(text)

            if (isApplySpan < 2) {   //text = builder 이후 한 번 더 불려야함
                val musicBoldSpan = StyleSpan(Typeface.BOLD)
                val nameIndex = builder.indexOf("님이")  //이름 추출
                val musicIndex = builder.indexOf(suffix)       // 곡 이름 추출
                if (nameIndex != -1 && musicIndex != -1) {
                    val nameColorSpan =
                        ForegroundColorSpan(resources.getColor(R.color.mument_color_blue1))
                    builder.setSpan(
                        nameColorSpan,
                        0,
                        nameIndex,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    builder.setSpan(
                        musicBoldSpan, nameIndex + 2, musicIndex,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
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