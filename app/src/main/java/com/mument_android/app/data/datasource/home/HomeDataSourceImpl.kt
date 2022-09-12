package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.network.home.HomeService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(val service: HomeService) : HomeDataSource {
    override suspend fun getBannerMument(): BaseResponse<BannerMumentDto> =
        service.getBannerMument()

    override suspend fun getTodayMument(userId: String): BaseResponse<TodayMumentDto>? =
        service.getTodayMument(userId)

    override suspend fun getKnownMument(): BaseResponse<KnownMumentDto> =
        service.getKnownMument()

    override suspend fun getRandomMument(): BaseResponse<RandomMumentDto> =
        service.getRandomMument()
}