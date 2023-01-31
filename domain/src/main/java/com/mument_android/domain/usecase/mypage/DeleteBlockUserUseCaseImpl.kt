package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.repository.mypage.BlockUserListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBlockUserUseCaseImpl @Inject constructor(
    private val blockUserListRepository: BlockUserListRepository
) : DeleteBlockUserUseCase {
    override suspend fun invoke(blockedUserId: String): Flow<Unit> =
        blockUserListRepository.deleteBlockUser(blockedUserId)
}