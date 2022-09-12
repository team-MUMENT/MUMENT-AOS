package com.startup.data.dto

import com.google.gson.annotations.SerializedName

data class MusicDto(
    @SerializedName("_id")
    val id: String,
    val artist: String,
    val image: String,
    val name: String
)