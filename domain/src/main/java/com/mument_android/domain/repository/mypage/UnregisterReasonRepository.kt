package com.mument_android.domain.repository.mypage

import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import kotlinx.coroutines.flow.Flow

interface UnregisterReasonRepository {
    suspend fun postUnregisterReason(
        leaveCategoryId: Int,
        reasonEtc: String
    ): Flow<UnregisterReasonEntity>
}