package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.TodayMumentEntity

data class TodayMumentDto(
    val todayDate: String,
    val todayMument: TodayMumentEntity
)