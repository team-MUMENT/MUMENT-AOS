package com.mument_android.app.data.controller

import com.mument_android.app.data.dto.record.MumentRecordDto
import com.mument_android.app.data.dto.record.ResponseRecordMumentDto
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.record.RecordApiService
import javax.inject.Inject

class RecordControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
): RecordController{
    override suspend fun recordMument(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto> = recordApiService.postMumentRecord(musicId, userId, mumentRecordDto)
}