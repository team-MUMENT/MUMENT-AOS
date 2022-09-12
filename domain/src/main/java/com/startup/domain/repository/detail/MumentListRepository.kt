package com.startup.domain.repository.detail

import com.startup.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.flow.Flow

interface MumentListRepository {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): Flow<List<MumentSummaryEntity>>
}