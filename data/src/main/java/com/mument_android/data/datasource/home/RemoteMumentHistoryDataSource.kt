package com.mument_android.data.datasource.home

import com.mument_android.data.dto.history.MumentHistoryDto
import com.mument_android.core.base.BaseResponse

interface RemoteMumentHistoryDataSource {
    suspend fun getMumentHistory(userId:String, musicId:String): BaseResponse<MumentHistoryDto>
}