package com.mument_android.data.local.recentlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recent_table")
data class RecentSearchDataEntity(
    @PrimaryKey
    val _id: String,
    val artist: String,
    val image: String,
    val name: String,
    val createAt: Date?,
)