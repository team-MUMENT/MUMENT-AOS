package com.mument_android.domain.entity.home

import android.os.Parcelable
import androidx.annotation.Keep
import com.mument_android.domain.entity.music.MusicInfoEntity
import kotlinx.parcelize.Parcelize
import java.util.Date

@Keep
@Parcelize
data class RecentSearchData(
    val _id: String,
    val artist: String,
    val image: String,
    val name: String,
    val createAt: Date?,
):Parcelable {
    fun toMusicInfo(): MusicInfoEntity {
        return MusicInfoEntity(
            id = _id,
            name = name,
            thumbnail = image,
            artist = artist
        )
    }
}