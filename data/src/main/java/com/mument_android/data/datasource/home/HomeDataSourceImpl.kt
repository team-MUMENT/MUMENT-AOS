package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.BaseResponse
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