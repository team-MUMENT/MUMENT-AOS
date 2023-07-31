package com.mument_android.data.usecase.main

import com.mument_android.domain.repository.main.LikeMumentRepository
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CancelLikeMumentUseCaseImpl @Inject constructor(
    private val likeMumentRepository: LikeMumentRepository
) : CancelLikeMumentUseCase {
    override suspend fun invoke(mumentId: String): Flow<Int> =
        likeMumentRepository.cancelLikeMument(mumentId)
}