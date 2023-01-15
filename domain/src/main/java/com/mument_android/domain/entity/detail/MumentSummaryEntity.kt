package com.mument_android.domain.entity.detail

import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.user.UserEntity

data class MumentSummaryEntity(
    val mumentId: String,
    val musicId: String,
    val user: UserEntity,
    val isFirst: Boolean,
    val tags: List<TagEntity>,
    val content: String?,
    val isPrivate: Boolean,
    val likeCount: Int,
    val isDeleted: Boolean,
    val createdAt: String,
    val date: String,
    val isLiked: Boolean
)