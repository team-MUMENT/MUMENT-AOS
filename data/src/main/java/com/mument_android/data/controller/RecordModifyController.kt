package com.mument_android.data.controller


import com.mument_android.data.dto.record.MumentModifyDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import com.mument_android.data.util.BaseResponse

interface RecordModifyController {
    suspend fun recordModifyMument(
        mumentId: String,
        mumentModifyDto: MumentModifyDto
    ): BaseResponse<ResponseRecordMumentDto>
}