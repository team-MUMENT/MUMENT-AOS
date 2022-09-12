package com.startup.data.dto.home

import com.startup.domain.entity.home.TodayMumentEntity

data class TodayMumentDto(
    val todayDate: String,
    val todayMument: TodayMumentEntity
)