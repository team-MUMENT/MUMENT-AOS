package com.startup.domain.usecase.record

import com.startup.domain.entity.record.MumentRecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordModifyMumentUseCase {
    suspend operator fun invoke(
        mumentId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<String>

}