package com.mument_android.data.dto.sign

data class NewTokenDto(
    val _id: Int?,
    val accessToken: String?,
    val refreshToken: String?,
    val type: String?
)