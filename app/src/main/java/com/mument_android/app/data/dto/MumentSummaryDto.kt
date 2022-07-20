package com.mument_android.app.data.dto

import com.google.gson.annotations.SerializedName

data class MumentSummaryDto(
    val music: MusicIdDto,
    val user: UserDto,
    @SerializedName("_id")
    val id: String,
    val isFirst: Boolean,
    val impressionTag: List<Int>?,
    val feelingTag: List<Int>?,
    val content: String,
    val isPrivate: Boolean,
    val likeCount: Int,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val cardTag: List<Int>,
    val date: String,
    val isLiked: Boolean
) {
    data class MusicIdDto(
        @SerializedName("_id")
        val id: String
    )
}