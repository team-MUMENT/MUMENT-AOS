package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.home.HomeService
import timber.log.Timber
import javax.inject.Inject

class RemoteMumentHistoryDataSourceImpl @Inject constructor(val homeService:HomeService):RemoteMumentHistoryDataSource {
    override suspend fun getMumentHistory(userId:String, musicId:String): BaseResponse<MumentHistoryDto> {
        val data = homeService.getMumentHistory(userId, musicId)
        Timber.d("Motion $data")
        return data
    }
}