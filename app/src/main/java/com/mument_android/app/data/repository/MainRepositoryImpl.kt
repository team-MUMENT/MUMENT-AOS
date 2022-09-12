package com.mument_android.app.data.repository

import com.mument_android.app.data.controller.LikeMumentController
import com.startup.domain.repository.main.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl(private val likeMumentController: LikeMumentController) : MainRepository {
    override suspend fun likeMument(mumentId: String, userId: String): Flow<Int> = flow {
        likeMumentController.likeMument(mumentId, userId).data?.likeCount?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    override suspend fun cancelLikeMument(
        mumentId: String,
        userId: String
    ): Flow<Int> = flow {
        likeMumentController.cancelLikeMument(mumentId, userId).data?.likeCount?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}