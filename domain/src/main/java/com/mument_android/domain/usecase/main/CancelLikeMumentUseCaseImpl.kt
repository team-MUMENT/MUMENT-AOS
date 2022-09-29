package com.mument_android.domain.usecase.main

import com.mument_android.domain.repository.main.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CancelLikeMumentUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : CancelLikeMumentUseCase {
    override suspend fun invoke(mumentId: String, userId: String): Flow<Int> =
        mainRepository.cancelLikeMument(mumentId, userId)
}