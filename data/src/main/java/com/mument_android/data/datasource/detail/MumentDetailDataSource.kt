package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MumentDetailDto
import com.mument_android.data.util.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MumentDetailDataSource {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): Flow<BaseResponse<MumentDetailDto>>
}