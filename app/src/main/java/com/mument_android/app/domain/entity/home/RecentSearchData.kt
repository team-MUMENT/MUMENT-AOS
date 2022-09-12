package com.mument_android.app.domain.entity.home

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recent_table")
data class RecentSearchData(
    @PrimaryKey
    val _id: String,
    val artist: String,
    val image: String,
    val name: String,
    val createAt: Date?,
){
    @Ignore
    val optional: Boolean = true
}