package com.mument_android.app.data.controller

import com.mument_android.app.data.dto.record.MumentRecordDto
import com.mument_android.app.data.dto.record.ResponseRecordMumentDto
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.record.RecordApiService
import javax.inject.Inject

class RecordModifyControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
): RecordModifyController{
    override suspend fun recordModifyMument(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto> = recordApiService.putMumentRecord(mumentId,mumentRecordDto)


}