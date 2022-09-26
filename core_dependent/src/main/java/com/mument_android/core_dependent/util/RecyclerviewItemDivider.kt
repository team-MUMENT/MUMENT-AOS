package com.mument_android.core_dependent.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewItemDivider(
    private val horizontalDiv: Int,
    private val verticalDiv: Int ,
    private val layoutType: String
    ): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val isLastItem = parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)
            when(layoutType) {
                IS_GRIDLAYOUT -> {
                    bottom = verticalDiv
                    if (!isLastItem) right = horizontalDiv
                }
                IS_VERTICAL -> {
                    if (!isLastItem) bottom = verticalDiv
                }
                IS_HORIZONTAL -> {
                    if (!isLastItem) right = horizontalDiv
                }
                else -> {}
            }
        }
    }

    companion object {
        const val IS_GRIDLAYOUT = "IS_GRIDLAYOUT"
        const val IS_HORIZONTAL = "IS_HORIZONTAL"
        const val IS_VERTICAL = "IS_VERTICAL"
    }
}