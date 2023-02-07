package com.mument_android.domain.entity.sign

data class NewTokenEntity(
    val _id: Int?,
    val accessToken: String?,
    val refreshToken: String?,
    val type: String?
)