package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.RequestReportDto
import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.util.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReportMumentDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): ReportMumentDataSource {
    override suspend fun reportMument(
        mumentId: String,
        requestReportDto: RequestReportDto
    ): BaseResponse<Void> {
        return detailApiService.reportMument(mumentId, requestReportDto)
    }
}