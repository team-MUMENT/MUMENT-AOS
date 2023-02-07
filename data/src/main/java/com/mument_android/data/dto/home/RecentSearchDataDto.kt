package com.mument_android.data.dto.home

import com.mument_android.data.local.recentlist.RecentSearchDataEntity

data class RecentSearchDataDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<RecentSearchDataEntity>
)