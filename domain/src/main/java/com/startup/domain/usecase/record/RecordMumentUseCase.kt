package com.startup.domain.usecase.record

import com.startup.domain.entity.record.MumentRecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordMumentUseCase {
    suspend operator fun invoke(
        musicId: String,
        userId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<String>
}