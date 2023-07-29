package com.mument_android.domain.usecase.record

import com.mument_android.domain.entity.record.MumentModifyEntity
import kotlinx.coroutines.flow.Flow

interface RecordModifyMumentUseCase {
    suspend operator fun invoke(
        mumentId: String,
        mumentModifyEntity: MumentModifyEntity
    ): Flow<String>

}