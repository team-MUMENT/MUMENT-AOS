package com.mument_android.app.domain.repository.record

import com.mument_android.app.domain.entity.record.MumentRecordEntity
import com.mument_android.app.domain.entity.record.RecordIsFirstEntity
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun fetchMumentRecord(userId: String, musicId: String): Flow<RecordIsFirstEntity>
    suspend fun updateMumentRecord(
        mumentId: String,
        mumentRecordDto: MumentRecordEntity
    ): Flow<String>
    suspend fun insertMumentRecord(
        musicId: String,
        userId: String,
        mumentRecordDto: MumentRecordEntity):Flow<String>
}