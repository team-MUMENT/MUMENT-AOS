package com.mument_android.app.domain.repository.main

import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun likeMument(mumentId: String, userId: String): Flow<Int>
    suspend fun cancelLikeMument(mumentId: String, userId: String): Flow<Int>
}