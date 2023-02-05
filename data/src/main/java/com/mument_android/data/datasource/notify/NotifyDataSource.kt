package com.mument_android.data.datasource.notify

import com.mument_android.data.dto.home.NotifyDto
import com.mument_android.data.util.ResultWrapper
import retrofit2.Response

interface NotifyDataSource {
    suspend fun fetchNotifyList(): Response<NotifyDto>
    suspend fun fetchNotifyRead(unreadNews: List<Int>): ResultWrapper<Void?>
    suspend fun fetchNotifyDelete(newsId: String): ResultWrapper<Void?>
}