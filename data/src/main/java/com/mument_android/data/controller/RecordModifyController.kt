package com.mument_android.data.controller


import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import com.mument_android.core.base.BaseResponse

interface RecordModifyController {
    suspend fun recordModifyMument(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto>
}