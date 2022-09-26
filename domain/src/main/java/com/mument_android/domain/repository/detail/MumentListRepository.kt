package com.mument_android.domain.repository.detail

import com.mument_android.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.flow.Flow

interface MumentListRepository {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): Flow<List<MumentSummaryEntity>>
}