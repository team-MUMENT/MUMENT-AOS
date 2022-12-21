package com.mument_android.data.datasource.home

import com.mument_android.core.network.ApiResult
import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(val service: HomeService) : HomeDataSource {
    override suspend fun getBannerMument(): ApiResult<BannerMumentDto?> {
        val data = service.getBannerMument()
        return if (data.success && data.data != null) {
            ApiResult.Success(data.data)
        } else {
            ApiResult.Failure(throwable = Throwable(data.message))
        }
    }

    override suspend fun getTodayMument(userId: String): ApiResult<TodayMumentDto?> {
        val data = service.getTodayMument(userId)
        return if (data.body() != null && data.isSuccessful) {
            ApiResult.Success(data.body())
        } else {
            ApiResult.Failure(throwable = Throwable(data.message()))
        }
    }

    override suspend fun getKnownMument(): ApiResult<KnownMumentDto?> {
        val data = service.getKnownMument()
        return if (data.success && data.data != null) {
            ApiResult.Success(data.data)
        } else {
            ApiResult.Failure(throwable = Throwable(data.message))
        }
    }

    override suspend fun getRandomMument(): ApiResult<RandomMumentDto?> {
        val data = service.getRandomMument()
        return if (data.success && data.data != null) {
            ApiResult.Success(data.data)
        } else {
            ApiResult.Failure(throwable = Throwable(data.message))
        }
    }
}