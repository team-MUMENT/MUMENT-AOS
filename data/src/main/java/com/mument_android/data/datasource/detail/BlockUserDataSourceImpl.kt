package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.ResponseBlockUserDto
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BlockUserDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
) : BlockUserDataSource {
    override suspend fun block(mumentId: String): Flow<ResultWrapper<ResponseBlockUserDto?>> =
        flow {
            emit(
                kotlin.runCatching {
                    detailApiService.blockUser(mumentId).let {
                        when (it.status) {
                            in 400..499 -> {
                                ResultWrapper.GenericError(it.status, it.message)
                            }
                            in 500..599 -> {
                                ResultWrapper.NetworkError
                            }
                            else -> {
                                ResultWrapper.Success(it)
                            }
                        }
                    }
                }.getOrElse { throwable ->
                    when (throwable) {
                        is IOException -> ResultWrapper.NetworkError
                        is HttpException -> ResultWrapper.GenericError(
                            throwable.code(),
                            throwable.message
                        )
                        else -> ResultWrapper.GenericError(null, throwable.message!!)
                    }
                })
        }
}