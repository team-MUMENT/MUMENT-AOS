package com.startup.data.controller


import com.startup.data.dto.record.MumentRecordDto
import com.startup.data.dto.record.ResponseRecordMumentDto
import com.startup.core.base.BaseResponse

interface RecordModifyController {
    suspend fun recordModifyMument(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto>
}