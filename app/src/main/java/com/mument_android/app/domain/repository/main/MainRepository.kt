package com.mument_android.app.domain.repository.main

import com.mument_android.app.data.dto.LikeCountDto
import com.startup.core.base.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun likeMument(mumentId: String, userId: String): Flow<Int>
    suspend fun cancelLikeMument(mumentId: String, userId: String): Flow<Int>
}