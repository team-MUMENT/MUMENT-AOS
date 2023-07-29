package com.mument_android.data.usecase.record

import com.mument_android.domain.entity.record.RecordIsFirstEntity
import com.mument_android.domain.repository.record.RecordRepository
import com.mument_android.domain.usecase.record.IsFirstRecordMumentUseCase
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

