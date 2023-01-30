package com.mument_android.data.dto.home

import com.mument_android.data.local.todaymument.TodayMumentEntity

data class TodayMumentDto(val status:Int, val success:String, val message:String,
    val todayDate: String,
    val todayMument: TodayMumentEntity
)