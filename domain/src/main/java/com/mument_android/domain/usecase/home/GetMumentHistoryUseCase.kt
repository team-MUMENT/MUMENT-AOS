package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.history.MumentHistoryEntity
import kotlinx.coroutines.flow.Flow

interface GetMumentHistoryUseCase {
    suspend fun getMumentHistory(userId:String, musicId:String): Flow<MumentHistoryEntity>
}