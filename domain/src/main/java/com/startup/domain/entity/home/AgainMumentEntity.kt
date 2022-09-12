package com.startup.domain.entity.home

import com.startup.domain.entity.musicdetail.musicdetaildata.Music
import com.startup.domain.entity.User

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