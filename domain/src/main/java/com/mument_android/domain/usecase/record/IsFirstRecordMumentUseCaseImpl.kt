package com.mument_android.domain.usecase.record

import com.mument_android.domain.entity.record.RecordIsFirstEntity
import com.mument_android.domain.repository.record.RecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFirstRecordMumentUseCaseImpl @Inject constructor(
    private val recordRepository: RecordRepository
) : IsFirstRecordMumentUseCase {
    override suspend operator fun invoke(
        musicId: String
    ): Flow<RecordIsFirstEntity> =
        recordRepository.fetchMumentRecord(musicId)
}

