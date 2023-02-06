package com.mument_android.domain.usecase.notify

import com.mument_android.domain.repository.notify.NotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchNotifyListsReadUseCaseImpl @Inject constructor(private val notifyRepository: NotifyRepository) :
    FetchNotifyListsReadUseCase {
    override suspend fun invoke(unreadNews: List<Int>): Flow<Boolean?> = flow {
        emit(notifyRepository.fetchNotifyRead(unreadNews))
    }
}