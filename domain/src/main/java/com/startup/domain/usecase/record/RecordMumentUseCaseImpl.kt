package com.startup.domain.usecase.record

import com.startup.domain.entity.record.MumentRecordEntity
import com.startup.domain.repository.record.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordMumentUseCaseImpl @Inject constructor(
    private val repository: RecordRepository
) : RecordMumentUseCase {
    override suspend fun invoke(
        musicId: String,
        userId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<String> = repository.insertMumentRecord(musicId, userId, mumentRecordEntity)
}