package com.mument_android.domain.entity.home

import androidx.annotation.Keep
import com.mument_android.domain.entity.music.MusicInfoEntity

@Keep
data class Like(
    val userName: String?,
    val music: Music
) {
    @Keep
    data class Music(
        val id: String?,
        val name: String?,
        val artist: String?,
        val image: String?
    )
    fun toMusicInfoEntity(): MusicInfoEntity? {
        with(music) {
            if (name != null && artist != null && image != null && id != null) {
                return MusicInfoEntity(id = id, name = name, thumbnail = image, artist = artist)
            }
            return null
        }
    }

}
