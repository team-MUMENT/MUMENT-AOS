package com.mument_android.app.data.datasource.record

import com.mument_android.app.data.dto.record.MumentIsFirstDto

interface RecordDataSource {
    suspend fun fetchMumentRecord(userId :String, musicId: String) : MumentIsFirstDto?
}