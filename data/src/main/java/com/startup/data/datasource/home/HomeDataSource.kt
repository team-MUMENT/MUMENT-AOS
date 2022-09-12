package com.startup.data.datasource.home

import com.startup.data.dto.home.BannerMumentDto
import com.startup.data.dto.home.KnownMumentDto
import com.startup.data.dto.home.RandomMumentDto
import com.startup.data.dto.home.TodayMumentDto
import com.startup.core.base.BaseResponse

interface HomeDataSource {
    suspend fun getBannerMument(): BaseResponse<BannerMumentDto>
    suspend fun getTodayMument(userId:String): BaseResponse<TodayMumentDto>?
    suspend fun getKnownMument(): BaseResponse<KnownMumentDto>
    suspend fun getRandomMument(): BaseResponse<RandomMumentDto>
}