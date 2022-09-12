package com.startup.data.datasource.detail

import com.startup.data.dto.detail.MumentDetailDto
import com.startup.core.base.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MumentDetailDataSource {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): Flow<BaseResponse<MumentDetailDto>>
}