package com.mument_android.data.dto.home

import com.mument_android.domain.entity.User
import com.mument_android.domain.entity.history.MusicX

data class TodayItem(
    val mumentId: Int,
    val music: MusicX,
    val user: User,
    val content: String,
    val isFirst: Boolean,
    val feelingTag: List<Int>,
    val impressionTag: List<Int>,
    val cardTag: List<Int>,
    val createdAt: String,
    val date:String,
    val displayDate: String,
)
