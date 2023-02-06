package com.mument_android.domain.repository.mypage

import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity
import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import kotlinx.coroutines.flow.Flow

interface UnregisterReasonRepository {
    suspend fun postUnregisterReason(
        requestUnregisterReasonEntity: RequestUnregisterReasonEntity
    ): Flow<UnregisterReasonEntity>
}