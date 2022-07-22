package com.mument_android.app.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id")
    val id: String,
    val image: String?,
    val name: String
)