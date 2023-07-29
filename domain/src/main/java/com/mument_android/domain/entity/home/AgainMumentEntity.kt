package com.mument_android.domain.entity.home

import com.mument_android.domain.entity.musicdetail.Music
import com.mument_android.domain.entity.User

data class AgainMumentEntity(
    val mumentId: String,
    val music: Music,
    val user: User,
    val content: String,
    val createdAt: String,
)