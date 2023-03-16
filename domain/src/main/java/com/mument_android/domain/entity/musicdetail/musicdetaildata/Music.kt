package com.mument_android.domain.entity.musicdetail.musicdetaildata

import android.os.Parcelable
import androidx.annotation.Keep
import com.mument_android.domain.entity.music.MusicInfoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Music(
    val _id: String,
    val name: String,
    val artist: String,
    val image: String
):Parcelable {
    fun toMusicInfoEntity(): MusicInfoEntity {
        return MusicInfoEntity(id = _id, name = name, thumbnail = image, artist = artist)
    }
}