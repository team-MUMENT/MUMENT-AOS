package com.mument_android.domain.entity.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicInfoEntity(
    val id: String,
    val name: String,
    val thumbnail: String,
    val artist: String,
): Parcelable