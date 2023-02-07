package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.datasource.notify.NotifyDataSource
import com.mument_android.data.mapper.notify.NotifyMapper
import com.mument_android.domain.entity.home.NotifyEntity
import com.mument_android.domain.repository.notify.NotifyRepository
import com.mument_android.domain.util.NetworkExtensions.callApi
import javax.inject.Inject

class NotifyRepositoryImpl @Inject constructor(
    private val notifyDataSource: NotifyDataSource,
    private val notifyMapper: NotifyMapper
) : NotifyRepository {
    override suspend fun fetchNotifyList(): ApiStatus<List<NotifyEntity>?> =
        callApi(notifyMapper) {
            notifyDataSource.fetchNotifyList()
        }

    override suspend fun fetchNotifyRead(unreadNews: List<Int>): Boolean? =
        notifyDataSource.fetchNotifyRead(unreadNews)


    override suspend fun fetchNotifyDelete(newsId: String): Boolean? =
        notifyDataSource.fetchNotifyDelete(newsId)
}