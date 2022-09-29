package com.mument_android.domain.usecase.record

import com.mument_android.domain.entity.record.MumentRecordEntity
import com.mument_android.domain.repository.record.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordModifyMumentUseCaseImpl @Inject constructor(
    private val repository: RecordRepository
) : RecordModifyMumentUseCase {
    override suspend fun invoke(
        mumentId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<String> = repository.updateMumentRecord(mumentId, mumentRecordEntity)
}