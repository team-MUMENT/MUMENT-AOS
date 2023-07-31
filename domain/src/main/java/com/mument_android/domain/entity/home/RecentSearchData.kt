package com.mument_android.domain.entity.home

import androidx.annotation.Keep
import com.mument_android.domain.entity.music.MusicInfoEntity
import java.io.Serializable
import java.util.Date

@Keep
data class RecentSearchData(
    val _id: String,
    val artist: String,
    val image: String,
    val name: String,
    val createAt: Date?,
): Serializable {
    fun toMusicInfo(): MusicInfoEntity {
        return MusicInfoEntity(
            id = _id,
            name = name,
            thumbnail = image,
            artist = artist
        )
    }
}