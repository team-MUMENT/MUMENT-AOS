package com.mument_android.app.domain.entity.detail

import com.mument_android.app.data.enumtype.EmotionalTag
import com.mument_android.app.data.enumtype.ImpressiveTag
import com.mument_android.app.domain.entity.album.AlbumEntity
import com.mument_android.app.domain.entity.user.UserEntity

data class MumentDetailEntity(
    val writerInfo: UserEntity,
    val albumInfo: AlbumEntity,
    val impressionTags: List<ImpressiveTag>?,
    val emotionalTags: List<EmotionalTag>?,
    val content: String,
    val createdDate: String,
    val isLiked: Boolean,
    val likeCount: Int
)