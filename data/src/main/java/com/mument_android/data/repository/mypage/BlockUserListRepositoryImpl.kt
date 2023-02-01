package com.mument_android.data.repository.mypage

import com.mument_android.data.controller.DeleteBlockUserController
import com.mument_android.data.datasource.mypage.BlockUserListDataSource
import com.mument_android.data.mapper.mypage.BlockUserListMapper
import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.repository.mypage.BlockUserListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BlockUserListRepositoryImpl @Inject constructor(
    private val blockUserListDataSource: BlockUserListDataSource,
    private val blockUserListMapper: BlockUserListMapper,
    private val deleteBlockUserController: DeleteBlockUserController
) : BlockUserListRepository {
    override suspend fun fetchBlockUserList(

    ): Flow<List<BlockUserEntity>> = flow {
        blockUserListDataSource.fetchBlockUserList()?.let {
            emit(blockUserListMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteBlockUser(blockedUserId: Int): Flow<Unit> = flow {
        deleteBlockUserController.deleteBlockUser(blockedUserId).let {
            emit(kotlin.run {  })
        }
    }
}