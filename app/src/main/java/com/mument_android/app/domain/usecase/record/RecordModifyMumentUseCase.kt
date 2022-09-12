package com.mument_android.app.domain.usecase.record

import com.mument_android.app.domain.entity.record.MumentRecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordModifyMumentUseCase {
    suspend operator fun invoke(
        mumentId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<String>

}