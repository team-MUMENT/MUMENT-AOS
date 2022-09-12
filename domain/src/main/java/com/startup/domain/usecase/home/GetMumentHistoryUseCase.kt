package com.startup.domain.usecase.home

import com.startup.domain.entity.history.MumentHistoryEntity
import kotlinx.coroutines.flow.Flow

interface GetMumentHistoryUseCase {
    suspend fun getMumentHistory(userId:String, musicId:String): Flow<MumentHistoryEntity>
}