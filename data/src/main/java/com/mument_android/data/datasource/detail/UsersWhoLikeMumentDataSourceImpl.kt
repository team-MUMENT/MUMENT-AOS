package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.UserDto
import com.mument_android.data.network.detail.DetailApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersWhoLikeMumentDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): UsersWhoLikeMumentDataSource {
    override fun fetchUsers(mumentId: String, limit: Int, offset: Int): Flow<List<UserDto>?> = flow {
        emit(detailApiService.fetchUsersWhoLikeMument(mumentId, limit, offset).data)
    }
}