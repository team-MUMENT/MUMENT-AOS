package com.mument_android.data.dto

data class LikedUserDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<TempUserDto>
)
