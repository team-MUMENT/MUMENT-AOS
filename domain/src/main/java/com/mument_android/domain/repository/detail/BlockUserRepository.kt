package com.mument_android.domain.repository.detail

import com.mument_android.core.network.ApiStatus
import kotlinx.coroutines.flow.Flow

interface BlockUserRepository {
    suspend fun block(mumentId: String): Flow<ApiStatus<Unit>>
}