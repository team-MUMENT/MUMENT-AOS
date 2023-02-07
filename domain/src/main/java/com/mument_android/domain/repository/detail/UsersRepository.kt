package com.mument_android.domain.repository.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun fetchUsers(mumentId: String, limit: Int, offset: Int): Flow<ApiStatus<List<UserEntity>>>
}