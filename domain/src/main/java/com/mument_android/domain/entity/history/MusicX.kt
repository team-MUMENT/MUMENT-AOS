package com.mument_android.domain.entity.history

import androidx.annotation.Keep

@Keep
data class MusicX(
    val _id: String,
    val name: String,
    val artist: String,
    val image: String
)