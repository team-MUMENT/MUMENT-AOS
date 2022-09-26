package com.mument_android.data.dto.home

import com.mument_android.domain.entity.User
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music

data class TodayMument(
    val _id: String,
    val cardTag: List<Int>,
    val content: String,
    val createdAt: String,
    val date: String,
    val displayDate: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val isFirst: Boolean,
    val mumentId: String,
    val music: Music,
    val user: User
)