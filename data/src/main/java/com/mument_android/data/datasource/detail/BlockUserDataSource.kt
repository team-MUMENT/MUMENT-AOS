package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.ResponseBlockUserDto
import kotlinx.coroutines.flow.Flow

interface BlockUserDataSource {
    suspend fun block(mumentId: String): Flow<ResponseBlockUserDto?>
}