package com.startup.data.datasource.record

import com.startup.data.dto.record.MumentIsFirstDto
import com.startup.data.network.record.RecordApiService
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(private val recordApiservice: RecordApiService) :
    RecordDataSource {
    override suspend fun fetchMumentRecord(userId: String, musicId: String): MumentIsFirstDto? {
        return recordApiservice.fetchMumentIsFirst(userId,musicId).data
    }
}