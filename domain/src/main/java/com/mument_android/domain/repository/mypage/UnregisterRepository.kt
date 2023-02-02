package com.mument_android.domain.repository.mypage

import com.mument_android.domain.entity.mypage.UnregisterEntity
import kotlinx.coroutines.flow.Flow

interface UnregisterRepository {
    suspend fun fetchUnregisterInfo(): Flow<UnregisterEntity>
}