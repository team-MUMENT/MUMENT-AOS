package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.BannerEntity

data class BannerMumentDto(
    val bannerList: List<BannerEntity>,
    val todayDate: String
)