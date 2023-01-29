package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.datasource.detail.BlockUserDataSource
import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.mapper.detail.BlockUserMapper
import com.mument_android.data.util.callApi
import com.mument_android.domain.repository.detail.BlockUserRepository
import javax.inject.Inject

class BlockUserRepositoryImpl @Inject constructor(
    private val blockUserDataSource: BlockUserDataSource,
    private val blockUserMapper: BlockUserMapper
): BlockUserRepository {
    override suspend fun block(mumentId: String): ApiStatus<Unit> {
        return callApi(blockUserMapper) {
            blockUserDataSource.block(mumentId)
        }
    }
}