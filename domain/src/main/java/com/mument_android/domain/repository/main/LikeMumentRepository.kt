package com.mument_android.domain.repository.main

import kotlinx.coroutines.flow.Flow

interface LikeMumentRepository {
    suspend fun likeMument(mumentId: String): Flow<Int>
    suspend fun cancelLikeMument(mumentId: String): Flow<Int>
}