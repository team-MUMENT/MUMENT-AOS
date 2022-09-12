package com.startup.data.datasource.home

import com.startup.data.dto.history.MumentHistoryDto
import com.startup.core.base.BaseResponse

interface RemoteMumentHistoryDataSource {
    suspend fun getMumentHistory(userId:String, musicId:String): BaseResponse<MumentHistoryDto>
}