package com.mument_android.data.dto.record

data class MumentRecordDto(
    val content: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val isPrivate: Boolean,
    val musicId: String,
    val musicArtist: String,
    val musicImage: String,
    val musicName: String
)