package com.mument_android.app.domain.entity.detail

import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.album.AlbumEntity
import com.mument_android.app.domain.entity.user.UserEntity

data class MumentDetailEntity(
    val writerInfo: UserEntity,
    val albumInfo: AlbumEntity,
    val isFirst: TagEntity,
    val impressionTags: List<TagEntity>?,
    val emotionalTags: List<TagEntity>?,
    val content: String?,
    val createdDate: String,
    val isLiked: Boolean,
    val mumentHistoryCount: Int,
    val likeCount: Int
) {
    fun combineTags(): List<TagEntity> {
        return mutableListOf<TagEntity>().also {
            it.add(isFirst)
            impressionTags?.let { impressionTags -> it.addAll(impressionTags) }
            emotionalTags?.let { emotionalTags -> it.addAll(emotionalTags) }
        }
    }
}