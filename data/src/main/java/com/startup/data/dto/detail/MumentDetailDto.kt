package com.startup.data.dto.detail

import com.startup.data.dto.MusicDto
import com.startup.data.dto.UserDto

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