package com.mument_android.detail.mument.fragment

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

class EllipsizeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /*maxHeight = measuredHeight
        for (i in 0 until  lineCount) {
            Log.e("Ellipsize", "${layout.getEllipsisStart(i)}")
        }
        Log.e("onMeasure", height.toString())
        Log.e("onMeasure measuredHeight", measuredHeight.toString())*/
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        /*Log.e("onLayout", height.toString())
        maxHeight = measuredHeight*/
    }
}