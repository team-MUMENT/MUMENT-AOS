package com.mument_android.data.controller

import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import com.mument_android.data.util.BaseResponse

interface RecordController {
    suspend fun recordMument(
        musicId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto>
}