package com.startup.domain.entity.musicdetail.musicdetaildata

import com.startup.domain.entity.User

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