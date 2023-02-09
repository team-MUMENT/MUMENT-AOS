package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BlockUserDataSource {
    suspend fun block(mumentId: String): Flow<ResultWrapper<ResponseBlockUserDto?>>
}