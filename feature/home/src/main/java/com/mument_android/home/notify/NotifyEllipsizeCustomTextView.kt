package com.mument_android.home.notify

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class NotifyEllipsizeCustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private val suffix = "...에 쓴 뮤멘트를 좋아합니다"
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (layout.getEllipsisStart(2) != 0) {
            val newText = text.removeRange(
                text.length - (layout.getEllipsisCount(2) + (suffix.length)),
                text.length
            )
            text = String.format("%s%s", newText, suffix)
            requestLayout()
        }
    }
}