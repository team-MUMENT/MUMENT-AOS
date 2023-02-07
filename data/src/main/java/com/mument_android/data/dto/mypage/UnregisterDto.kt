package com.mument_android.data.dto.mypage

data class UnregisterDto(
    val id : Int,
    val profileId :String,
    val isDeleted :Boolean,
    val updatedAt : String
)
