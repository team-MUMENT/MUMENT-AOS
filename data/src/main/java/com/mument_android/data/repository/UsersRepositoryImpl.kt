package com.mument_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mument_android.data.datasource.detail.LikeMumentPagingSourceFactory
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.domain.entity.user.UserEntity
import com.mument_android.domain.repository.detail.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val detailApiService: DetailApiService,
) : UsersRepository {
    override suspend fun fetchUsers(
        mumentId: String
    ): Flow<PagingData<UserEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            LikeMumentPagingSourceFactory(
                detailApiService,
                mumentId
            )
        }
    ).flow
}