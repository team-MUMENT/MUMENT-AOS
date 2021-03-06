package com.mument_android.app.domain.entity.music

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicInfoEntity(
    val id: String,
    val name: String,
    val thumbnail: String,
    val artist: String,
): Parcelable