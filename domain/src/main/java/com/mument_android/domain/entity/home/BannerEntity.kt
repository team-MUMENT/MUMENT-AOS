package com.mument_android.domain.entity.home

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music

data class BannerEntity(
    val displayDate: String,
    val music: Music,
    val tagTitle: String
)
