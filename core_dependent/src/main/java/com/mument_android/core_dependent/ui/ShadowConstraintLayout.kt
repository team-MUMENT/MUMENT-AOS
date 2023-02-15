package com.mument_android.core_dependent.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mument_android.core_dependent.R

class ShadowConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    init {
        outlineAmbientShadowColor =
            ContextCompat.getColor(context, R.color.mument_card_shadow_color)
        outlineSpotShadowColor = ContextCompat.getColor(context, R.color.mument_card_shadow_color)
    }
}