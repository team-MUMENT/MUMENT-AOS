package com.mument_android.app.data.dto.home

import com.mument_android.app.domain.entity.home.AgainMumentEntity

data class KnownMumentDto(
    val againMumentEntity: List<AgainMumentEntity>,
    val todayDate: String
)