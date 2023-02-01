package com.mument_android.data.dto.sign

data class SetProfileDto(
    val id : Int,
    val accessToken : String,
    val refreshToken : String,
    val profileId : String,
    val image : String
)