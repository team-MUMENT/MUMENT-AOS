package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.datasource.detail.UsersWhoLikeMumentDataSource
import com.mument_android.data.mapper.user.UserMapper
import com.mument_android.domain.entity.user.UserEntity
import com.mument_android.domain.repository.detail.UsersRepository
import com.mument_android.domain.util.ApiStatusExtensions.toApiStatus
import com.mument_android.domain.util.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersWhoLikeMumentDataSource: UsersWhoLikeMumentDataSource,
    private val errorHandler: ErrorHandler
): UsersRepository {
    override suspend fun fetchUsers(
        mumentId: String,
        limit: Int,
        offset: Int
    ): Flow<ApiStatus<List<UserEntity>>> =
        usersWhoLikeMumentDataSource.fetchUsers(mumentId, limit, offset)
            .map { it?.map { it.toUserEntity() } ?: throw NullPointerException("Can't receive user data") }
            .toApiStatus(errorHandler)
}