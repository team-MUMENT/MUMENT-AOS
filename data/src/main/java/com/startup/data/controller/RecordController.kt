package com.startup.data.controller
import com.startup.data.dto.record.MumentRecordDto
import com.startup.data.dto.record.ResponseRecordMumentDto
import com.startup.core.base.BaseResponse

interface RecordController {
    suspend fun recordMument(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto>
}