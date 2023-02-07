package com.mument_android.domain.usecase.record

import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.domain.entity.record.MumentRecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordModifyMumentUseCase {
    suspend operator fun invoke(
        mumentId: String,
        mumentModifyEntity: MumentModifyEntity
    ): Flow<String>

}