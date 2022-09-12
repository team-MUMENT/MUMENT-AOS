package com.startup.domain.entity.detail

import com.startup.domain.entity.user.UserEntity

data class MumentSummaryEntity(
    val mumentId: String,
    val musicId: String,
    val user: UserEntity,
    val isFirst: Boolean,
    val impressionTags: List<Int>,
    val emotionalTags: List<Int>,
    val content: String?,
    val isPrivate: Boolean,
    val likeCount: Int,
    val isDeleted: Boolean,
    val date: String,
    val cardTag: List<Int>,
    val isLiked: Boolean
)