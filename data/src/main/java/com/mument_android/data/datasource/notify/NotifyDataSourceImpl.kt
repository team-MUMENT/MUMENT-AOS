package com.mument_android.data.datasource.notify

import com.mument_android.data.dto.home.NotifyDto
import com.mument_android.data.network.home.NotifyService
import com.mument_android.data.util.ResultWrapper
import com.mument_android.data.util.catchingApiCall
import retrofit2.Response
import javax.inject.Inject

class NotifyDataSourceImpl @Inject constructor(private val notifyService: NotifyService) :
    NotifyDataSource {
    override suspend fun fetchNotifyList(): Response<NotifyDto> = notifyService.fetchNotifyList()

    override suspend fun fetchNotifyRead(unreadNews: List<Int>): ResultWrapper<Void?> =
        catchingApiCall { notifyService.fetchNotifyRead(unreadNews) }

    override suspend fun fetchNotifyDelete(newsId: String): ResultWrapper<Void?> =
        catchingApiCall { notifyService.fetchNotifyDelete(newsId) }
}