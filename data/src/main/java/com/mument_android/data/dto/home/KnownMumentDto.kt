package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.AgainMumentEntity

data class KnownMumentDto(val status:Int, val success:String, val message:String,
    val againMumentEntity: List<AgainMumentEntity>,
    val todayDate: String
)