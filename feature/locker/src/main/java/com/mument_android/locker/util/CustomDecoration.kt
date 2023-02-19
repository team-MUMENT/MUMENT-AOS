package com.mument_android.locker.util

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView


class CustomDecoration(
    private val height: Float,
    private val padding: Float,
    @ColorInt
    private val color: Int
):RecyclerView.ItemDecoration() {
    private val paint = Paint()

    init{
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart + padding
        val right = parent.width - parent.paddingEnd - padding

        for(i in 0 until parent.childCount){
            if (i == parent.childCount - 1)
                break
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top+height

            c.drawRect(left, top, right, bottom, paint)
        }
    }
}
