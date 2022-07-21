package com.mument_android.app.data.dto.home

import com.mument_android.app.data.dto.MusicDto
import com.mument_android.app.data.dto.UserDto

data class RandomMumentDto(
    val _id: String,
    val content: String,
    val createdAt: String,
    val isDeleted: String,
    val music: MusicDto,
    val user: UserDto
)
