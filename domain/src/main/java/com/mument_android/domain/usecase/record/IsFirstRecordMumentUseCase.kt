package com.mument_android.domain.usecase.record

import com.mument_android.domain.entity.record.RecordIsFirstEntity
import kotlinx.coroutines.flow.Flow

interface IsFirstRecordMumentUseCase {
    suspend operator fun invoke(musicId: String): Flow<RecordIsFirstEntity>
}