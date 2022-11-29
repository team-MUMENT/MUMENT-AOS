package com.mument_android.domain.repository.detail

import com.mument_android.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface MumentDetailRepository {
    suspend fun fetchMumentDetail(mumentId: String): Flow<MumentDetailEntity?>
    suspend fun deleteMument(mumentId: String):Flow<Unit>
}