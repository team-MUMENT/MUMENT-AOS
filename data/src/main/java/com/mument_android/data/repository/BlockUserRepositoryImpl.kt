package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.datasource.detail.BlockUserDataSource
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.util.ErrorHandler
import com.mument_android.domain.util.NetworkExtensions.toApiStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BlockUserRepositoryImpl @Inject constructor(
    private val blockUserDataSource: BlockUserDataSource,
    private val errorHandler: ErrorHandler
): BlockUserRepository {
    override suspend fun block(mumentId: String): Flow<ApiStatus<Unit>> =
        blockUserDataSource.block(mumentId)
            .map {  }
            .toApiStatus(errorHandler)
}