package com.mument_android.domain.repository.notify

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.home.NotifyEntity

interface NotifyRepository {
    suspend fun fetchNotifyList(): ApiStatus<List<NotifyEntity>?>
    suspend fun fetchNotifyRead(unreadNews: List<Int>): Boolean?
    suspend fun fetchNotifyDelete(newsId: String): Boolean?
}