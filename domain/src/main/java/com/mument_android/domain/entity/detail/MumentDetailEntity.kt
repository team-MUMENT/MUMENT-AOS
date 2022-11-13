package com.mument_android.domain.entity.detail

import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.music.MusicInfoEntity
import com.mument_android.domain.entity.user.UserEntity
import java.io.Serializable

data class MumentDetailEntity(
    val writerInfo: UserEntity,
    val musicInfo: MusicInfoEntity,
    val isFirst: TagEntity,
    val impressionTags: List<TagEntity>?,
    val emotionalTags: List<TagEntity>?,
    val content: String?,
    val createdDate: String,
    val isLiked: Boolean,
    val mumentHistoryCount: Int,
    val likeCount: Int
): Serializable {
    fun combineTags(): List<TagEntity> {
        return mutableListOf<TagEntity>().also {
            it.add(isFirst)
            impressionTags?.let { impressionTags -> it.addAll(impressionTags) }
            emotionalTags?.let { emotionalTags -> it.addAll(emotionalTags) }
        }
    }
}