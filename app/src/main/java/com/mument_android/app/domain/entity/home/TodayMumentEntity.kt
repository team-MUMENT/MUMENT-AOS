package com.mument_android.app.domain.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "today_mument_table")
data class TodayMumentEntity(
    @PrimaryKey
    val userId: String,
    val userName: String,
    val userImage: String,
    val cardTag: List<Int>,
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
    val todayDate: String,
)