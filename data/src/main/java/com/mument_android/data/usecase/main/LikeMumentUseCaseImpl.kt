package com.mument_android.data.usecase.main

import com.mument_android.domain.repository.main.LikeMumentRepository
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeMumentUseCaseImpl @Inject constructor(
    private val likeMumentRepository: LikeMumentRepository
) : LikeMumentUseCase {
    override suspend fun invoke(mumentId: String): Flow<Int> =
        likeMumentRepository.likeMument(mumentId)
}