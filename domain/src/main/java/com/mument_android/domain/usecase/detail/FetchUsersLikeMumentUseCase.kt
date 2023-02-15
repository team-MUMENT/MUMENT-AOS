package com.mument_android.domain.usecase.detail

import androidx.paging.PagingData
import com.mument_android.domain.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

interface FetchUsersLikeMumentUseCase {
    suspend operator fun invoke(mumentId: String): Flow<PagingData<UserEntity>>
}