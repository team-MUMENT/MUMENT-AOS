package com.mument_android.app.data.dto

import com.mument_android.app.domain.entity.MumentCardData.Music
import com.mument_android.app.domain.entity.MumentCardData.User
import java.time.Year

data class MumentCard(
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
    val year: String?,
    val month : String?
)