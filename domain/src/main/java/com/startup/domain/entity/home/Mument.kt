package com.startup.domain.entity.home

import com.startup.domain.entity.musicdetail.musicdetaildata.Music
import com.startup.domain.entity.User

data class Mument(
    val _id: String,
    val content: String,
    val createdAt: String,
    val music: Music,
    val user: User
)