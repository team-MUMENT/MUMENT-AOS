package com.mument_android.app.data.dto.home

import com.mument_android.app.data.local.todaymument.todaymumentdata.Music
import com.mument_android.app.data.local.todaymument.todaymumentdata.User

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