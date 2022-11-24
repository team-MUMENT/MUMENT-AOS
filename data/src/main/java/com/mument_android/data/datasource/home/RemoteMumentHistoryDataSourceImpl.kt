package com.mument_android.data.datasource.home

import com.mument_android.data.dto.history.MumentHistoryDto
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class RemoteMumentHistoryDataSourceImpl @Inject constructor(val homeService:HomeService):
    RemoteMumentHistoryDataSource {
    override suspend fun getMumentHistory(userId:String, musicId:String): BaseResponse<MumentHistoryDto> {
        val data = homeService.getMumentHistory(userId, musicId)
        return data
    }
}