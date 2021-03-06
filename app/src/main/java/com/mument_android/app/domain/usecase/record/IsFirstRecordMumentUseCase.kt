package com.mument_android.app.domain.usecase.record

import com.mument_android.app.domain.entity.record.RecordIsFirstEntity
import kotlinx.coroutines.flow.Flow

interface IsFirstRecordMumentUseCase {
    suspend operator fun invoke(userId: String, musicId: String): Flow<RecordIsFirstEntity>
}