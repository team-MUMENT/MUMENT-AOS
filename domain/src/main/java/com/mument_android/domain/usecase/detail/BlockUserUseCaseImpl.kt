package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.repository.detail.BlockUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockUserUseCaseImpl @Inject constructor(
    private val blockUserRepository: BlockUserRepository
): BlockUserUseCase {
    override suspend fun invoke(mumentId: String): Flow<ApiStatus<Unit>> =
        blockUserRepository.block(mumentId)
}