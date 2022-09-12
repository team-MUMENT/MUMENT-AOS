package com.startup.domain.usecase.detail

import com.startup.domain.entity.detail.MumentSummaryEntity
import kotlinx.coroutines.flow.Flow


interface FetchMumentListUseCase {
    suspend operator fun invoke(musicId: String, userId: String, default: String): Flow<List<MumentSummaryEntity>>
}