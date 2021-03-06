package com.mument_android.app.domain.repository.detail

import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.flow.Flow

interface MumentListRepository {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): Flow<List<MumentSummaryEntity>>
}