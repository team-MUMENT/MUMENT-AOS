package com.mument_android.app.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewItemDivider(private val horizontalDiv: Int, private val verticalDiv: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
                right = horizontalDiv
                bottom = verticalDiv
            }
        }
    }
}