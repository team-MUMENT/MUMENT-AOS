package com.mument_android.app.data.local

import com.mument_android.app.data.local.todaymumentdata.Music
import com.mument_android.app.data.local.todaymumentdata.User

data class TodayMumentEntity(
    val _id: String,
    val content: String,
    val createdAt: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isDeleted: Boolean,
    val isFirst: Boolean,
    val isLiked: Boolean,
    val isPrivate: Boolean,
    val likeCount: Int,
    val music: Music,
    val user: User
)