package com.mument_android.app.data.datasource.record

import com.mument_android.app.data.dto.record.MumentIsFirstDto
import com.mument_android.app.data.network.record.RecordApiService
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(private val recordApiservice: RecordApiService) : RecordDataSource {
    override suspend fun fetchMumentRecord(userId: String, musicId: String): MumentIsFirstDto {
        return recordApiservice.fetchMumentIsFirst(userId,musicId).data
    }
}