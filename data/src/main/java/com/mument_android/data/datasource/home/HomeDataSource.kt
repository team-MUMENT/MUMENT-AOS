package com.mument_android.data.datasource.home

import com.mument_android.core.network.ApiResult
import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.util.BaseResponse

interface HomeDataSource {
    suspend fun getBannerMument(): ApiResult<BannerMumentDto?>
    suspend fun getTodayMument(userId:String): ApiResult<TodayMumentDto?>
    suspend fun getKnownMument(): ApiResult<KnownMumentDto?>
    suspend fun getRandomMument(): ApiResult<RandomMumentDto?>
}