package com.mument_android.data.controller

import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import com.mument_android.data.network.record.RecordApiService
import com.mument_android.core.base.BaseResponse
import javax.inject.Inject

class RecordModifyControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
): RecordModifyController {
    override suspend fun recordModifyMument(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto> = recordApiService.putMumentRecord(mumentId,mumentRecordDto)


}