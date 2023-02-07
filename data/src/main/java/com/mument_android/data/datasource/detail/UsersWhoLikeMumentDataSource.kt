package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.TempUserDto
import com.mument_android.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UsersWhoLikeMumentDataSource {
    fun fetchUsers(mumentId: String, limit: Int, offset: Int): Flow<List<TempUserDto>?>
}