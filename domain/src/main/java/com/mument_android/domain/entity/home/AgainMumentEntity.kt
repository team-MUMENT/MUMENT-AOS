package com.mument_android.domain.entity.home

import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.entity.User

data class AgainMumentEntity(
    val _id: String,
    val content: String,
    val createdAt: String,
    val displayDate: String,
    val isFirst: Boolean,
    val mumentId: String,
    val music: Music,
    val user: User
)