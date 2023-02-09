package com.mument_android.domain.usecase.record

import com.mument_android.domain.entity.record.MumentRecordEntity
import com.mument_android.domain.repository.record.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordMumentUseCaseImpl @Inject constructor(
    private val repository: RecordRepository
) : RecordMumentUseCase {
    override suspend fun invoke(
        musicId: String,
        mumentRecordEntity: MumentRecordEntity
    ): Flow<Pair<String, Int>> = repository.insertMumentRecord(musicId, mumentRecordEntity)
}