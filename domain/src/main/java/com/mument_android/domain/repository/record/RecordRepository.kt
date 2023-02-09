package com.mument_android.domain.repository.record

import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.domain.entity.record.MumentRecordEntity
import com.mument_android.domain.entity.record.RecordIsFirstEntity
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun fetchMumentRecord(musicId: String): Flow<RecordIsFirstEntity>
    suspend fun updateMumentRecord(
        mumentId: String,
        mumentModifyEntity: MumentModifyEntity
    ): Flow<String>

    suspend fun insertMumentRecord(
        musicId: String,
        mumentRecordDto: MumentRecordEntity
    ): Flow<Pair<String, Int>>
}