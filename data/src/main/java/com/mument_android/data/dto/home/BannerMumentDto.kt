package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.BannerEntity

data class BannerMumentDto(
    val status:Int, val success:String, val message:String,
    val bannerList: List<BannerEntity>,
    val todayDate: String
)