package com.mument_android.data.datasource.home

import com.mument_android.data.dto.history.MumentHistoryDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import javax.inject.Inject

class RemoteMumentHistoryDataSourceImpl @Inject constructor(val homeService: HomeService) :
    RemoteMumentHistoryDataSource {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): ResultWrapper<MumentHistoryDto?> =
        catchingApiCall { homeService.getMumentHistory(userId, musicId) }
}