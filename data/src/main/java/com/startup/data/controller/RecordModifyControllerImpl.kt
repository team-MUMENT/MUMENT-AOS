package com.startup.data.controller

import com.startup.data.dto.record.MumentRecordDto
import com.startup.data.dto.record.ResponseRecordMumentDto
import com.startup.data.network.record.RecordApiService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class RecordModifyControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
): RecordModifyController {
    override suspend fun recordModifyMument(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto> = recordApiService.putMumentRecord(mumentId,mumentRecordDto)


}