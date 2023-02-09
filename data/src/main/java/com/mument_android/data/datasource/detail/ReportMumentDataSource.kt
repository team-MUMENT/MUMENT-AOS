package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.RequestReportDto
import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.util.BaseResponse
import kotlinx.coroutines.flow.Flow

interface ReportMumentDataSource {
    suspend fun reportMument(mumentId: String, requestReportDto: RequestReportDto): BaseResponse<Void>
}