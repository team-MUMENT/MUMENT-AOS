package com.mument_android.app.data.dto.history

data class MumentHistory(
    val __v: Int,
    val _id: String,
    val cardTag: List<Int>,
    val content: String,
    val createdAt: String,
    val date: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isDeleted: Boolean,
    val isFirst: Boolean,
    val isLiked: Boolean,
    val isPrivate: Boolean,
    val likeCount: Int,
    val music: Music,
    val updatedAt: String,
    val user: User
)