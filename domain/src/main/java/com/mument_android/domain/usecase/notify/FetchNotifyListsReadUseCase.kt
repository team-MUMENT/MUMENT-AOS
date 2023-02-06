package com.mument_android.domain.usecase.notify

import kotlinx.coroutines.flow.Flow

interface FetchNotifyListsReadUseCase {
    suspend operator fun invoke(unreadNews: List<Int>): Flow<Boolean?>
}