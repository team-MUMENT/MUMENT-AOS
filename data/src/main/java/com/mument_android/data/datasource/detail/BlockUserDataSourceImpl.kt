package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.network.detail.DetailApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BlockUserDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): BlockUserDataSource {
    override suspend fun block(mumentId: String): Flow<ResponseBlockUserDto?> = flow {
        emit(detailApiService.blockUser(mumentId).data)
    }
}