package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.network.detail.DetailApiService
import retrofit2.Response
import javax.inject.Inject

class BlockUserDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): BlockUserDataSource {
    override suspend fun block(mumentId: String): Response<ResponseBlockUserDto> {
        return detailApiService.blockUser(mumentId)
    }
}