package com.mument_android.domain.usecase.detail

import com.mument_android.domain.entity.history.MumentHistory
import kotlinx.coroutines.flow.Flow

interface GetMumentHistoryUseCase {
    suspend fun getMumentHistory(userId:String, musicId:String, default:String): Flow<List<MumentHistory>?>
}