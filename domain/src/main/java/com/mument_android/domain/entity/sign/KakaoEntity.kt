package com.mument_android.domain.entity.sign

data class KakaoEntity(
    val _id : Int,
    val type : String,
    val accessToken : String,
    val refreshToken : String
)
