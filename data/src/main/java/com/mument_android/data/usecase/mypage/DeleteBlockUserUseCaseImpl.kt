package com.mument_android.data.usecase.mypage

import com.mument_android.domain.repository.mypage.BlockUserListRepository
import com.mument_android.domain.usecase.mypage.DeleteBlockUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBlockUserUseCaseImpl @Inject constructor(
    private val blockUserListRepository: BlockUserListRepository
) : DeleteBlockUserUseCase {
    override suspend fun invoke(blockedUserId: Int): Flow<Unit> {
        return blockUserListRepository.deleteBlockUser(blockedUserId)
    }
}