package com.startup.domain.usecase.record

import com.startup.domain.entity.record.MumentRecordEntity
import com.startup.domain.repository.record.RecordRepository
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