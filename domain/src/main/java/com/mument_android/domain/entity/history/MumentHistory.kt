package com.mument_android.domain.entity.history

import com.mument_android.domain.entity.User
data class MumentHistory(
    val _id: Int,
    val musicId: String,
    val user: User,
    val isFirst: Boolean,
    val impressionTag: List<Int>?,
    val feelingTag: List<Int>?,
    val cardTag: List<Int>?,
    val content: String,
    val isPrivate: Boolean,
    val likeCount: Int,
    val isDeleted: Boolean,
    val createAt: String,
    val updatedAt: String,
    val date: String,
    var isLiked: Boolean,
)