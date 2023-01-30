package com.mument_android.domain.usecase.notify

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.home.NotifyEntity
import kotlinx.coroutines.flow.Flow

interface FetchNotifyListUseCase {
    suspend operator fun invoke(): Flow<ApiStatus<List<NotifyEntity>?>>
}