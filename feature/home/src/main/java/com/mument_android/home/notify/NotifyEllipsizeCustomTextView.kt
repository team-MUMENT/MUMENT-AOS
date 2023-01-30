package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mument_android.home.R
import com.mument_android.home.util.NotifyType
import com.mument_android.home.util.TextStyleCustomHelper

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private lateinit var textStyleCustomHelper: TextStyleCustomHelper
    private var isApplySpanLimit = 0
    lateinit var notiType: NotifyType

    fun clearSpanCount() {
        isApplySpanLimit = 0
    }

    override fun draw(canvas: Canvas?) {
        if (text != null) {
            textStyleCustomHelper = TextStyleCustomHelper(text)
            val builder =
                textStyleCustomHelper.setEllipsizeMusic(layout = layout) ?: SpannableStringBuilder(
                    text
                )
            //text = builder 이후 한 번 더 불려야함
            if (isApplySpanLimit < 2) {
                if (::notiType.isInitialized && notiType == NotifyType.LIKE && textStyleCustomHelper.applyNotifySpan(
                        text = builder,
                        nameColor = context.getColor(R.color.mument_color_blue1)
                    )
                ) {
                    text = builder
                }
                isApplySpanLimit++
            }
        } else {
            isApplySpanLimit = 0
        }
        super.draw(canvas)
    }

    fun setTypeOfNotify(type: NotifyType) {
        notiType = type
    }
}