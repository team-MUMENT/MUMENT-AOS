package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.network.base.BaseResponse

interface RemoteMumentHistoryDataSource {
    suspend fun getMumentHistory(userId:String, musicId:String):BaseResponse<MumentHistoryDto>
}