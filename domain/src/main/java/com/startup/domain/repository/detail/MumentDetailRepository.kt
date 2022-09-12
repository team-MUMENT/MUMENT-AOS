package com.startup.domain.repository.detail

import com.startup.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface MumentDetailRepository {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): Flow<MumentDetailEntity?>
    suspend fun deleteMument(mumentId: String):Flow<Unit>
}