package com.mument_android.data.datasource.notify

import android.util.Log
import com.mument_android.data.dto.home.NotifyDto
import com.mument_android.data.dto.home.RequestReadNotify
import com.mument_android.data.network.home.NotifyService
import retrofit2.Response
import javax.inject.Inject

class NotifyDataSourceImpl @Inject constructor(private val notifyService: NotifyService) :
    NotifyDataSource {
    override suspend fun fetchNotifyList(): Response<NotifyDto> = notifyService.fetchNotifyList()

    override suspend fun fetchNotifyRead(unreadNews: List<Int>): Boolean? =
        runCatching {
            Log.e("fetchNotifyRead", unreadNews.toString())
            notifyService.fetchNotifyRead(RequestReadNotify(unreadNews)).code() == 204
        }.getOrElse {
            null
        }

    override suspend fun fetchNotifyDelete(newsId: String): Boolean? =
        runCatching { notifyService.fetchNotifyDelete(newsId).code() == 200 }.getOrElse {
            null
        }
}