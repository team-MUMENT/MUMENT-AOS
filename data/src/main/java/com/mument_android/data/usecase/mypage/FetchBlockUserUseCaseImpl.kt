package com.mument_android.data.usecase.mypage

import com.mument_android.domain.entity.mypage.BlockUserEntity
import com.mument_android.domain.repository.mypage.BlockUserListRepository
import com.mument_android.domain.usecase.mypage.FetchBlockUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchBlockUserUseCaseImpl @Inject constructor(
    private val blockUserListRepository: BlockUserListRepository
) : FetchBlockUserUseCase {
    override suspend fun invoke(): Flow<List<BlockUserEntity>> =
        blockUserListRepository.fetchBlockUserList()
}

