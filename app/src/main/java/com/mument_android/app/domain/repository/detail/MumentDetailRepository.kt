package com.mument_android.app.domain.repository.detail

import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface MumentDetailRepository {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): Flow<MumentDetailEntity?>
    suspend fun deleteMument(mumentId: String):Flow<Unit>
}