package com.mument_android.app.domain.repository.detail

import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface MumentDetailRepository {
    suspend fun fetchMumentDetail(mumentId: Int, userId: Int): Flow<ApiResult<MumentDetailEntity>>
}