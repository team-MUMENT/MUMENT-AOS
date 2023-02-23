package com.mument_android.domain.repository.mypage

import kotlinx.coroutines.flow.Flow

interface UnregisterRepository {
    suspend fun fetchUnregisterInfo(): Flow<Boolean>
}