package com.startup.data.datasource.home

import com.startup.data.dto.history.MumentHistoryDto
import com.startup.data.network.home.HomeService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class RemoteMumentHistoryDataSourceImpl @Inject constructor(val homeService:HomeService):
    RemoteMumentHistoryDataSource {
    override suspend fun getMumentHistory(userId:String, musicId:String): BaseResponse<MumentHistoryDto> {
        val data = homeService.getMumentHistory(userId, musicId)
        return data
    }
}