package com.mument_android.domain.entity.home

import com.mument_android.core.model.TagEntity
import com.mument_android.domain.entity.music.MusicInfoEntity

data class TodayMument(
    val userId: String,
    val userName: String,
    val userImage: String?,
    val cardTag: List<TagEntity>,
    val content: String,
    val createdAt: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val displayDate: String,
    val isFirst: Boolean,
    val date: String,
    val mumentId: String,
    val musicId: String,
    val musicName: String?,
    val musicArtist: String,
    val musicImage: String,
    val todayDate: String
) {
    fun extractMusicInfo(): MusicInfoEntity {
        return MusicInfoEntity(musicId, musicName ?: "", musicImage, musicArtist)
    }
}