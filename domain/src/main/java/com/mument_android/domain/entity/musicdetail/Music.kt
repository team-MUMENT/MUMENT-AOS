package com.mument_android.domain.entity.musicdetail

import androidx.annotation.Keep
import com.mument_android.domain.entity.music.MusicInfoEntity
import java.io.Serializable

@Keep
data class Music(
    val _id: String,
    val name: String,
    val artist: String,
    val image: String
) : Serializable {
    fun toMusicInfoEntity(): MusicInfoEntity {
        return MusicInfoEntity(id = _id, name = name, thumbnail = image, artist = artist)
    }
}