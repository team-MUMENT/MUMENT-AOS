package com.mument_android.domain.entity.music

import android.os.Parcelable
import com.mument_android.domain.entity.detail.MusicReqeust
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicInfoEntity(
    val id: String,
    val name: String,
    val thumbnail: String,
    val artist: String,
): Parcelable {
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

