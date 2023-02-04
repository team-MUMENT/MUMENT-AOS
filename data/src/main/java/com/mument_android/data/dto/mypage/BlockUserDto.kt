package com.mument_android.data.dto.mypage

import com.google.gson.annotations.SerializedName

data class BlockUserDto(
    val id: Int,
    @SerializedName("profile_id")
    val profileId: String,
    val image: String
)
