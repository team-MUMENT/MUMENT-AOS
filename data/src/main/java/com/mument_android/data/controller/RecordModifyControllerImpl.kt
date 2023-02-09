package com.mument_android.data.controller

import com.mument_android.data.dto.record.MumentModifyDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import com.mument_android.data.network.record.RecordApiService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class RecordModifyControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
) : RecordModifyController {
    override suspend fun recordModifyMument(
        mumentId: String,
        mumentModifyDto: MumentModifyDto
    ): BaseResponse<ResponseRecordMumentDto> =
        recordApiService.putMumentRecord(mumentId, mumentModifyDto)


}