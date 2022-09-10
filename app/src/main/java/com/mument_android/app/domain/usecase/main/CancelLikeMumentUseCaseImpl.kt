package com.mument_android.app.domain.usecase.main

import com.mument_android.app.data.controller.LikeMumentController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CancelLikeMumentUseCaseImpl @Inject constructor(
    private val likeMumentController: LikeMumentController
): CancelLikeMumentUseCase {
    override suspend fun invoke(mumentId: String, userId: String): Flow<Int> = flow {
        likeMumentController.cancelLikeMument(mumentId, userId).data?.likeCount?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}