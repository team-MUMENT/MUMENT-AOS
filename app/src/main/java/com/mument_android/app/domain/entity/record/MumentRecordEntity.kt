package com.mument_android.app.domain.entity.record

data class MumentRecordEntity(
    val content: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val isPrivate: Boolean
)
