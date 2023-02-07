package com.mument_android.domain.entity.sign

data class SetProfileEntity(
    //필요성 긴가민가해서 우선 다 넣기
    val id : Int,
    val accessToken : String,
    val refreshToken : String,
    val userName : String,
    val image : String?
)