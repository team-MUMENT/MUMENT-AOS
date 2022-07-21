package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import kotlinx.coroutines.flow.Flow

interface GetMumentHistoryUseCase {
    suspend fun getMumentHistory(userId:String, musicId:String): Flow<MumentHistoryDto>
}