package com.mument_android.domain.entity.musicdetail

import com.mument_android.domain.entity.User

data class MyMument(
    val _id: String,
    val content: String,
    val date: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isDeleted: Boolean,
    val isFirst: Boolean,
    val isLiked: Boolean,
    val isPrivate: Boolean,
    val likeCount: Int,
    val user: User
)