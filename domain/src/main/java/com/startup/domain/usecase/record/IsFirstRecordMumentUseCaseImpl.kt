package com.startup.domain.usecase.record

import com.startup.domain.entity.record.RecordIsFirstEntity
import com.startup.domain.repository.record.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFirstRecordMumentUseCaseImpl @Inject constructor(
    private val recordRepository: RecordRepository
) : IsFirstRecordMumentUseCase {
    override suspend operator fun invoke(
        userId: String,
        musicId: String
    ): Flow<RecordIsFirstEntity> =
        recordRepository.fetchMumentRecord(userId, musicId)
}

