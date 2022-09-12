package com.startup.data.datasource.record

import com.startup.data.dto.record.MumentIsFirstDto

interface RecordDataSource {
    suspend fun fetchMumentRecord(userId :String, musicId: String) : MumentIsFirstDto?
}