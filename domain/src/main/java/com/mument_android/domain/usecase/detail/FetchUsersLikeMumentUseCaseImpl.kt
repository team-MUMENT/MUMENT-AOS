package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.user.UserEntity
import com.mument_android.domain.repository.detail.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersLikeMumentUseCaseImpl @Inject constructor(
    private val usersLikeMumentRepository: UsersRepository
) : FetchUsersLikeMumentUseCase {
    override suspend fun invoke(
        mumentId: String,
        limit: Int,
        offset: Int
    ): Flow<ApiStatus<List<UserEntity>>> = usersLikeMumentRepository.fetchUsers(mumentId, limit, offset)
}