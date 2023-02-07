package com.mument_android.domain.entity.record

data class MumentModifyEntity(
    val content: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val isPrivate: Boolean
)
