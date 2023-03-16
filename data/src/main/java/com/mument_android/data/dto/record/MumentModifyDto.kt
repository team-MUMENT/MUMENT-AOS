package com.mument_android.data.dto.record

import androidx.annotation.Keep

@Keep
data class MumentModifyDto(
    val content: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val isPrivate: Boolean
)
