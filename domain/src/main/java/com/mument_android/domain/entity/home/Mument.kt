package com.mument_android.domain.entity.home

import com.mument_android.domain.entity.musicdetail.Music
import com.mument_android.domain.entity.User

data class Mument(
    val _id: String,
    val content: String,
    val createdAt: String,
    val music: Music,
    val user: User
)