package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.detail.MumentDetailDto
import com.startup.core.base.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MumentDetailDataSource {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): Flow<BaseResponse<MumentDetailDto>>
}