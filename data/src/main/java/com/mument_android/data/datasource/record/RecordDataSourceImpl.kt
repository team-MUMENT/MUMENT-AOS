package com.mument_android.data.datasource.record

import com.mument_android.data.dto.record.MumentIsFirstDto
import com.mument_android.data.network.record.RecordApiService
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(private val recordApiservice: RecordApiService) :
    RecordDataSource {
    override suspend fun fetchMumentRecord(musicId: String): MumentIsFirstDto? {
        return recordApiservice.fetchMumentIsFirst(musicId).data
    }
}