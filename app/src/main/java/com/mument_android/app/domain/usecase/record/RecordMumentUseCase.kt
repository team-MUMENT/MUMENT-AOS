package com.mument_android.app.domain.usecase.record

import com.mument_android.app.domain.entity.record.MumentRecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordMumentUseCase {
    suspend operator fun invoke(
        musicId: String,
        userId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<String>
}