package com.mument_android.data.repository

import com.mument_android.data.datasource.mypage.BlockUserListDataSource
import com.mument_android.data.mapper.mypage.BlockUserListMapper
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.repository.BlockUserListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BlockUserListRepositoryImpl @Inject constructor(
    private val blockUserListDataSource: BlockUserListDataSource,
    private val blockUserListMapper: BlockUserListMapper
) : BlockUserListRepository {
    override suspend fun fetchBlockUserList(

    ): Flow<List<BlockUserEntity>> = flow {
        blockUserListDataSource.fetchBlockUserList()?.let {
            emit(blockUserListMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)

}