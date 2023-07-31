package com.mument_android.data.util

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.network.ApiStatus
import com.mument_android.core.network.ErrorMessage
import com.mument_android.domain.util.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkExtensions {
    suspend inline fun <T> Flow<T>.toApiStatus(
        handler: ErrorHandler
    ): Flow<ApiStatus<T>> = this.onStart {
        ApiStatus.Loading
    }.map {
        ApiStatus.Success(it) as ApiStatus<T>
    }.catch { t ->
        emit(ApiStatus.Failure(handler.handleError(t), t.message))
    }


    suspend inline fun <T, F> callApi(
        mapper: BaseMapper<T, F>,
        crossinline apiCall: suspend () -> Response<T>,
    ): ApiStatus<F?> {
        return kotlin.runCatching {
            ApiStatus.Success(
                apiCall().body()?.let { mapper.map(it) }
            )
        }.getOrElse { t ->
            when (t) {
                is HttpException -> when (t.code()) {
                    400 -> ApiStatus.Failure(ErrorMessage.INVALID, t.message())
                    401 -> ApiStatus.Failure(ErrorMessage.UNAUTHORIZED, t.message())
                    404 -> ApiStatus.Failure(ErrorMessage.NOT_FOUND, t.message())
                    403 -> ApiStatus.Failure(ErrorMessage.FORBIDDEN, t.message())
                    409 -> ApiStatus.Failure(ErrorMessage.CONFLICT, t.message())
                    500 -> ApiStatus.Failure(ErrorMessage.SERVER_ERROR, t.message())
                    else -> ApiStatus.Failure(ErrorMessage.UNKNOWN_ERROR, t.message())
                }
                is ConnectException, is SocketTimeoutException, is UnknownHostException -> ApiStatus.Failure(ErrorMessage.NETWORK_ERROR, t.message.toString())
                else -> ApiStatus.Failure(ErrorMessage.UNKNOWN_ERROR, "$t")
            }
        }
    }
}
