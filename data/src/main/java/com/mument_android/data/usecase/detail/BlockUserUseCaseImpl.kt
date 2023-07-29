package com.mument_android.data.usecase.detail

import android.util.Log
import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.usecase.detail.BlockUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class BlockUserUseCaseImpl @Inject constructor(
    private val blockUserRepository: BlockUserRepository
) : BlockUserUseCase {
    override suspend fun invoke(mumentId: String): Flow<ApiStatus<Unit>> =
        blockUserRepository.block(mumentId).catch { Log.e("CATCH", it.toString()) }
}