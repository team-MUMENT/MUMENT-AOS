package com.mument_android.data.datasource.record

import com.mument_android.data.dto.record.MumentIsFirstDto

interface RecordDataSource {
    suspend fun fetchMumentRecord(userId :String, musicId: String) : MumentIsFirstDto?
}