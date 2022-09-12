package com.startup.domain.usecase.record

import com.startup.domain.entity.record.RecordIsFirstEntity
import kotlinx.coroutines.flow.Flow

interface IsFirstRecordMumentUseCase {
    suspend operator fun invoke(userId: String, musicId: String): Flow<RecordIsFirstEntity>
}