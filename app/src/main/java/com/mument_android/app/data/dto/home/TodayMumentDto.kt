package com.mument_android.app.data.dto.home

import com.mument_android.app.domain.entity.home.TodayMumentEntity

data class TodayMumentDto(
    val todayDate: String,
    val todayMument: TodayMumentEntity
)