package com.mument_android.home.models

import com.mument_android.core.model.TagEntity

data class TodayMument(
    val userId: String,
    val userName: String,
    val userImage: String,
    val cardTag: List<TagEntity>,
    val _id: String,
    val content: String,
    val createdAt: String,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val displayDate: String,
    val isFirst: Boolean,
    val date: String,
    val mumentId: String,
    val musicId: String,
    val musicName: String,
    val musicArtist: String,
    val musicImage: String,
    val todayDate: String
)