package com.mument_android.domain.repository.detail

import androidx.paging.PagingData
import com.mument_android.domain.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun fetchUsers(mumentId: String): Flow<PagingData<UserEntity>>
}