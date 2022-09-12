package com.startup.domain.entity.home

import com.startup.domain.entity.musicdetail.musicdetaildata.Music

data class BannerEntity(
    val _id: String,
    val displayDate: String,
    val music: Music,
    val tagTitle: String
)
