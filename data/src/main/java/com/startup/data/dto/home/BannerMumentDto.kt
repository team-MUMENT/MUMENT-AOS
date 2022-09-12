package com.startup.data.dto.home

import com.startup.domain.entity.home.BannerEntity

data class BannerMumentDto(
    val bannerList: List<BannerEntity>,
    val todayDate: String
)