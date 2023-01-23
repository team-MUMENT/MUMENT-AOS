package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.repository.detail.BlockUserRepository
import javax.inject.Inject

class BlockUserUseCaseImpl @Inject constructor(
    private val blockUserRepository: BlockUserRepository
): BlockUserUseCase {
    override suspend fun invoke(mumentId: String): ApiStatus<Unit> =
        blockUserRepository.block(mumentId)
}