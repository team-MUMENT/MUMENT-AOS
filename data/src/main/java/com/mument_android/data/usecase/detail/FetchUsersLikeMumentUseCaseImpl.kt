package com.mument_android.data.usecase.detail

import androidx.paging.PagingData
import com.mument_android.domain.entity.user.UserEntity
import com.mument_android.domain.repository.detail.UsersRepository
import com.mument_android.domain.usecase.detail.FetchUsersLikeMumentUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersLikeMumentUseCaseImpl @Inject constructor(
    private val usersLikeMumentRepository: UsersRepository
) : FetchUsersLikeMumentUseCase {
    override suspend fun invoke(
        mumentId: String
    ): Flow<PagingData<UserEntity>> = usersLikeMumentRepository.fetchUsers(mumentId)
}