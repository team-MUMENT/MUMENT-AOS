package com.mument_android.domain.entity.detail

import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.user.UserEntity

data class MumentEntity(
    val writerInfo: UserEntity,
    val isFirst: TagEntity,
    val impressionTags: List<TagEntity>?,
    val emotionalTags: List<TagEntity>?,
    val content: String?,
    val createdDate: String,
    val isPrivate: Boolean
) {
    fun combineTags(): List<TagEntity> {
        return mutableListOf<TagEntity>().also {
            it.add(isFirst)
            impressionTags?.let { impressionTags -> it.addAll(impressionTags) }
            emotionalTags?.let { emotionalTags -> it.addAll(emotionalTags) }
        }
    }
}