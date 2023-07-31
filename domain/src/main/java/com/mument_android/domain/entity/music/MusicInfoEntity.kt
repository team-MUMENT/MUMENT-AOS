package com.mument_android.domain.entity.music

import androidx.annotation.Keep
import com.mument_android.domain.entity.detail.MusicReqeust
import com.mument_android.domain.entity.musicdetail.Music
import java.io.Serializable

@Keep
data class MusicInfoEntity(
    val id: String,
    val name: String,
    val thumbnail: String,
    val artist: String,
): Serializable {
    fun toMusic(): Music {
        return Music(
            _id = id,
            name = name,
            artist = artist,
            image = thumbnail
        )
    }
    fun toMusicRequest(): MusicReqeust {
        return MusicReqeust(musicId = id.toInt(), musicArtist = artist, musicImage = thumbnail, musicName = name)
    }
}

