package com.mument_android.domain.entity.home

import java.util.Date

data class RecentSearchData(
    val _id: String,
    val artist: String,
    val image: String,
    val name: String,
    val createAt: Date?,
)