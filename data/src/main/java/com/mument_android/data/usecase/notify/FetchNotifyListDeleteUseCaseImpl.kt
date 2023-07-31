package com.mument_android.data.usecase.notify

import com.mument_android.domain.repository.notify.NotifyRepository
import com.mument_android.domain.usecase.notify.FetchNotifyListDeleteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNotifyListDeleteUseCaseImpl @Inject constructor(private val notifyRepository: NotifyRepository) :
    FetchNotifyListDeleteUseCase {
    override suspend fun invoke(newsId: String): Flow<Boolean?> = flow {
        emit(notifyRepository.fetchNotifyDelete(newsId))
    }
}