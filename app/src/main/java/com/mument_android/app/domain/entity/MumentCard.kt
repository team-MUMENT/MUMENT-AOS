package com.mument_android.app.domain.entity

data class MumentCard(
    val _id: String,
    val content: String,
    val createdAt: String,
    val music_Id: String,
    val musicImage: String,
    val musicName: String,
    val musicArtist: String,
    val user_Id: String,
    val userImage: String?,
    val userName: String,
    val likeCount: Int?,
    val isLiked: Boolean?,
    val isPrivate: Boolean?,
    val isFirst: Boolean?,
    val tag1: Int,
    val tag2: Int,
)