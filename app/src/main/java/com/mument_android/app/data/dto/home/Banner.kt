package com.mument_android.app.data.dto.home

import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music

data class Banner(
    val _id: String,
    val displayDate: String,
    val music: Music,
    val tagTitle: String
)