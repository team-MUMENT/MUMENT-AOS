package com.mument_android.data.repository

import android.util.Log
import com.mument_android.core.network.ApiStatus
import com.mument_android.core.network.ErrorMessage
import com.mument_android.data.datasource.detail.BlockUserDataSource
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.util.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BlockUserRepositoryImpl @Inject constructor(
    private val blockUserDataSource: BlockUserDataSource,
    private val errorHandler: ErrorHandler
) : BlockUserRepository {
    override suspend fun block(mumentId: String): Flow<ApiStatus<Unit>> =
        blockUserDataSource.block(mumentId).map { response ->
            Log.e("RESPONSE", response.toString())
            when (response) {
                is ResultWrapper.GenericError -> {
                    when (response.code) {
                        400 -> {
                            ApiStatus.Failure(ErrorMessage.INVALID, "이미 차단한 유저입니다")
                        }
                        else -> {
                            ApiStatus.Failure(ErrorMessage.UNKNOWN_ERROR, response.message)
                        }
                    }
                }
                is ResultWrapper.Success -> {
                    ApiStatus.Success(Unit)
                }
                else -> {
                    ApiStatus.Failure(ErrorMessage.UNKNOWN_ERROR, "UnKnown")
                }
            }
        }
}