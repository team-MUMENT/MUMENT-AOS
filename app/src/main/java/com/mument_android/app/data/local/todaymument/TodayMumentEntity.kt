package com.mument_android.app.data.local.todaymument

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mument_android.app.data.local.todaymument.todaymumentdata.Music
import com.mument_android.app.data.local.todaymument.todaymumentdata.User

@Entity(tableName = "today_mument_table")
data class TodayMumentEntity(
    @PrimaryKey
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