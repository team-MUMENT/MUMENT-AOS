package com.mument_android.app.domain.usecase.record

import com.mument_android.app.data.dto.record.MumentRecordDto
import kotlinx.coroutines.flow.Flow

interface RecordModifyMumentUseCase {
    suspend operator fun invoke(
        mumentId: String,
        mumentRecordDto: MumentRecordDto
    ): Flow<String>

}