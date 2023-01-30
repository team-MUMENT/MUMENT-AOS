package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.ResponseBlockUserDto
import retrofit2.Response

interface BlockUserDataSource {
    suspend fun block(mumentId: String): Response<ResponseBlockUserDto>
}