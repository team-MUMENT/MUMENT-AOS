package com.mument_android.data.dto

import com.google.gson.annotations.SerializedName

data class MumentSummaryDto(
    val musicId: String,
    val user: UserDto,
    @SerializedName("_id")
    val id: String,
    val isFirst: Boolean,
    val impressionTag: List<Int>?,
    val feelingTag: List<Int>?,
    val cardTag: List<Int>?,
    val content: String?,
    val isPrivate: Boolean,
    val likeCount: Int,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val date: String,
    val isLiked: Boolean
) {
    data class MusicIdDto(
        @SerializedName("_id")
        val id: String?
    )
}