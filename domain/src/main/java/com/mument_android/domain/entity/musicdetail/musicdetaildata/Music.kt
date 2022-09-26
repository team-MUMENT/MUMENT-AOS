package com.mument_android.domain.entity.musicdetail.musicdetaildata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    val _id: String,
    val name: String,
    val artist: String,
    val image: String
):Parcelable