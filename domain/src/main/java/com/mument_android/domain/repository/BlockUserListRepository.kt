package com.mument_android.domain.repository

import com.mument_android.domain.entity.mypage.BlockUserEntity
import kotlinx.coroutines.flow.Flow

interface BlockUserListRepository {
    suspend fun fetchBlockUserList() : Flow<List<BlockUserEntity>>
}