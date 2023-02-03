package com.mument_android.data.dto.home

import com.mument_android.domain.entity.home.BannerEntity

data class Banner(
    val todayDate: String,
    val bannerList: List<BannerEntity>
)