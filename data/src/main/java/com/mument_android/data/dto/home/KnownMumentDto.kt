package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.AgainMumentEntity

data class KnownMumentDto(
    val againMumentEntity: List<AgainMumentEntity>,
    val todayDate: String
)