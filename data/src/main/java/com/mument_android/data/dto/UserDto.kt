package com.mument_android.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val image: String?
)