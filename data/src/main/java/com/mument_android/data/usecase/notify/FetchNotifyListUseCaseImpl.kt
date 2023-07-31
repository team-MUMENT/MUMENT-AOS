package com.mument_android.data.usecase.notify

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.home.NotifyEntity
import com.mument_android.domain.repository.notify.NotifyRepository
import com.mument_android.domain.usecase.notify.FetchNotifyListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNotifyListUseCaseImpl @Inject constructor(private val notifyRepository: NotifyRepository) :
    FetchNotifyListUseCase {
    override suspend fun invoke(): Flow<ApiStatus<List<NotifyEntity>?>> = flow {
        emit(notifyRepository.fetchNotifyList())
    }
}