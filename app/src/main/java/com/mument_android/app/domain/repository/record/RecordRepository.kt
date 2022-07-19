package com.mument_android.app.domain.repository.record

import com.mument_android.app.domain.entity.record.RecordIsFirstEntity
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    suspend fun fetchMumentRecord(userId : String, mumentId:String) : Flow<RecordIsFirstEntity>
}