package com.mument_android.app.data.dto.home

import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.app.domain.entity.musicdetail.musicdetaildata.User

data class AgainMument(
    val _id: String,
    val content: String,
    val createdAt: String,
    val displayDate: String,
    val isFirst: Boolean,
    val mumentId: String,
    val music: Music,
    val user: User
)