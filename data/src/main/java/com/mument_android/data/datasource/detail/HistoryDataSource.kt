package com.mument_android.data.datasource.detail

import com.mument_android.domain.entity.history.MumentHistory

interface HistoryDataSource {
    suspend fun getMumentHistory(userId: String, musicId: String, default: String):List<MumentHistory>?
}