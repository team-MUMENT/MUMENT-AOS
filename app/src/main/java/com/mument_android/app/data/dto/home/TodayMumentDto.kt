package com.mument_android.app.data.dto.home

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.dto.UserDto

data class TodayMumentDto(
    val _id: String,
    val content: String,
    val createdAt: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isDeleted: Boolean,
    val isFirst: Boolean,
    val isLiked: Boolean,
    val isPrivate: Boolean,
    val likeCount: Int,
    val music: MusicDto,
    val user: UserDto
)