package com.mument_android.home.util

import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle

data class SpanInfo(
    val builder: SpannableStringBuilder,
    val styleSpan: CharacterStyle,
    val spanType: SpanType,
    val suffix: List<String>
)
