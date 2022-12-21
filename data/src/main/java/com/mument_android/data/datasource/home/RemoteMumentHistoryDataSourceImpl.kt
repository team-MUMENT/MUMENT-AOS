package com.mument_android.data.datasource.home

import com.mument_android.core.network.ApiResult
import com.mument_android.data.dto.history.MumentHistoryDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class RemoteMumentHistoryDataSourceImpl @Inject constructor(val homeService: HomeService) :
    RemoteMumentHistoryDataSource {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): ApiResult<MumentHistoryDto?> {
        val data = homeService.getMumentHistory(userId, musicId)
        return if (data.success && data.data != null) {
            ApiResult.Success(data.data)
        } else {
            ApiResult.Failure(throwable = Throwable(data.message))
        }
    }
}