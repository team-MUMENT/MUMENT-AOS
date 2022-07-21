package com.mument_android.app.domain.entity.detail

import android.os.Parcelable
import com.mument_android.app.domain.entity.TagEntity
import com.mument_android.app.domain.entity.music.MusicInfoEntity
import com.mument_android.app.domain.entity.user.UserEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable {
    fun combineTags(): List<TagEntity> {
        return mutableListOf<TagEntity>().also {
            it.add(isFirst)
            impressionTags?.let { impressionTags -> it.addAll(impressionTags) }
            emotionalTags?.let { emotionalTags -> it.addAll(emotionalTags) }
        }
    }
}