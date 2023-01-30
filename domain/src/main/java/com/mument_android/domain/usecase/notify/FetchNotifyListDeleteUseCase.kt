package com.mument_android.domain.usecase.notify

import kotlinx.coroutines.flow.Flow

interface FetchNotifyListDeleteUseCase {
    suspend operator fun invoke(newsId: String): Flow<Boolean?>
}