package com.mument_android.app.data.dto

data class ResponseMumentDetailDto(
    val content: String?,
    val count: Int,
    val createdAt: String,
    val isFirst: Boolean,
    val feelingTag: List<Int>?,
    val impressionTag: List<Int>?,
    val isLiked: Boolean,
    val likeCount: Int,
    val music: MusicDto,
    val user: UserDto
)