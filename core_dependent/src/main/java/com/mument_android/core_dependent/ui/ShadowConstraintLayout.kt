package com.mument_android.core_dependent.ui

import android.content.Context
import android.graphics.Outline
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mument_android.core_dependent.R

class ShadowConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val cornerRadius = 11F
    private val shadowColor = ContextCompat.getColor(context, R.color.mument_card_shadow_color)
    private val shadowSize = 8F

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            outlineAmbientShadowColor = shadowColor
            outlineSpotShadowColor = shadowColor
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = ShadowOutlineProvider()
            clipToOutline = true
        }
    }

    inner class ShadowOutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val rect = RectF(
                shadowSize,
                shadowSize,
                view.width - shadowSize,
                view.height - shadowSize
            )
            val path = Path()
            path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
            outline.setConvexPath(path)
        }
    }
}