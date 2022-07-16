package com.mument_android.app.domain.usecase.main

import android.util.Log
import com.mument_android.app.data.controller.LikeMumentController
import com.mument_android.app.data.network.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class LikeMumentUseCaseImpl @Inject constructor(
    private val likeMumentController: LikeMumentController
): LikeMumentUseCase {
    override suspend fun invoke(mumentId: String, userId: String): Flow<Int> = flow {
        emit(likeMumentController.likeMument(mumentId, userId).data.likeCount)
    }.flowOn(Dispatchers.IO)
}