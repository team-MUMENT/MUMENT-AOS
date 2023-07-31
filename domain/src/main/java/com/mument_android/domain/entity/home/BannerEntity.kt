package com.mument_android.domain.entity.home

import androidx.annotation.Keep
import com.mument_android.domain.entity.musicdetail.Music

@Keep
data class BannerEntity(
    val displayDate: String,
    val music: Music,
    val tagTitle: String
)
