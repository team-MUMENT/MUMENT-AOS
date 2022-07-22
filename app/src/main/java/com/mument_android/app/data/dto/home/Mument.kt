package com.mument_android.app.data.dto.home

import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.User


data class Mument(
    val _id: String,
    val content: String,
    val createdAt: String,
    val music: Music,
    val user: User
)