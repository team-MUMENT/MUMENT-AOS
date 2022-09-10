package com.mument_android.app.domain.usecase.record

import com.mument_android.app.data.dto.record.MumentRecordDto
//TODO data layer remove
import kotlinx.coroutines.flow.Flow

interface RecordMumentUseCase {
    suspend operator fun invoke(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordDto
    ): Flow<String>
}