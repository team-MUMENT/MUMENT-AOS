package com.mument_android.data.dto.detail

import com.mument_android.data.dto.MusicDto
import com.mument_android.data.dto.UserDto

data class MumentDetailDto(
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