package com.mument_android.app.domain.usecase.main

import com.mument_android.app.data.controller.LikeMumentController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LikeMumentUseCaseImpl @Inject constructor(
    private val likeMumentController: LikeMumentController
): LikeMumentUseCase {
    override suspend fun invoke(mumentId: String, userId: String): Flow<Int> = flow {
        likeMumentController.likeMument(mumentId, userId).data?.likeCount?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}