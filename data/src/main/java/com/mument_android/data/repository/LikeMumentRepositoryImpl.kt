package com.mument_android.data.repository

import com.mument_android.data.controller.LikeMumentDataSource
import com.mument_android.domain.repository.main.LikeMumentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LikeMumentRepositoryImpl @Inject constructor(private val likeMumentController: LikeMumentDataSource) : LikeMumentRepository {
    override suspend fun likeMument(mumentId: String): Flow<Int> = flow {
        likeMumentController.likeMument(mumentId).data?.likeCount?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    override suspend fun cancelLikeMument(
        mumentId: String
    ): Flow<Int> = flow {
        likeMumentController.cancelLikeMument(mumentId).data?.likeCount?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}