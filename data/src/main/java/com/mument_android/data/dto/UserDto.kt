package com.mument_android.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val image: String?
)