package com.mument_android.app.domain.usecase.record

import com.mument_android.app.domain.entity.record.RecordIsFirstEntity
import com.mument_android.app.domain.repository.record.RecordRepository
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

