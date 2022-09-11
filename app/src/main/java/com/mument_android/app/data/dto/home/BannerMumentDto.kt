package com.mument_android.app.data.dto.home

import com.mument_android.app.domain.entity.home.BannerEntity

data class BannerMumentDto(
    val bannerList: List<BannerEntity>,
    val todayDate: String
)