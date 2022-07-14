package com.mument_android.app.domain.entity

import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User

data class TestLockerMumentCard(
    val _id: String,
    val content: String,
    val createdAt: String,
    val music: Music,
    val user: User,
    val likeCount: Int?,
    val isPrivate: Boolean?,
    val isFirst: Boolean?,
    val impression: List<Int>?,
    val feeling: List<Int>?,
    val isLiked : Boolean,
    val year: String,
    val month: String
)
