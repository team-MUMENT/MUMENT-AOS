package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.util.BaseResponse

interface HomeDataSource {
    suspend fun getBannerMument(): BaseResponse<BannerMumentDto>
    suspend fun getTodayMument(userId:String): BaseResponse<TodayMumentDto>?
    suspend fun getKnownMument(): BaseResponse<KnownMumentDto>
    suspend fun getRandomMument(): BaseResponse<RandomMumentDto>
}