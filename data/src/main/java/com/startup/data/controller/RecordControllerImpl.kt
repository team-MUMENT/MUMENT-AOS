package com.startup.data.controller

import com.startup.data.dto.record.MumentRecordDto
import com.startup.data.dto.record.ResponseRecordMumentDto
import com.startup.data.network.record.RecordApiService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class RecordControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
): RecordController {
    override suspend fun recordMument(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto> = recordApiService.postMumentRecord(musicId, userId, mumentRecordDto)
}