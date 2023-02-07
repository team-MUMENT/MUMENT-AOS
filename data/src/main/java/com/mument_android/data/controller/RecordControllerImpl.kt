package com.mument_android.data.controller

import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import com.mument_android.data.network.record.RecordApiService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class RecordControllerImpl @Inject constructor(
    private val recordApiService: RecordApiService
) : RecordController {
    override suspend fun recordMument(
        musicId: String,
        mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto> =
        recordApiService.postMumentRecord(musicId, mumentRecordDto)
}