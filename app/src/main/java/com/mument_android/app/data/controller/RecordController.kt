package com.mument_android.app.data.controller
import com.mument_android.app.data.dto.record.MumentRecordDto
import com.mument_android.app.data.dto.record.ResponseRecordMumentDto
import com.startup.core.base.BaseResponse

interface RecordController {
    suspend fun recordMument(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto>
}