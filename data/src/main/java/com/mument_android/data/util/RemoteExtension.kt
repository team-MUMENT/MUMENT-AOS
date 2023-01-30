package com.mument_android.data.util

import com.mument_android.core.base.BaseMapper
import com.mument_android.core.network.ApiStatus
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <T> catchingApiCall(crossinline apiCall: suspend () -> Response<T>): ResultWrapper<T?> {
    return runCatching {
        ResultWrapper.Success(apiCall().body())
    }.getOrElse { throwable ->
        when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> ResultWrapper.GenericError(throwable.code(), throwable.message)
            else -> ResultWrapper.GenericError(null, throwable.message!!)
        }
    }
}

suspend inline fun <T, F> callApi(
    mapper: BaseMapper<T, F>,
    crossinline apiCall: suspend () -> Response<T>,
): ApiStatus<F> {
    return kotlin.runCatching {
        ApiStatus.Success(
            apiCall().body()?.let { mapper.map(it) }
        )
    }.getOrElse { t ->
        when(t) {
            is HttpException -> when(t.code()) {
                400 -> ApiStatus.Failure(ApiStatus.HttpErrorType.INVALID, t.message())
                401 -> ApiStatus.Failure(ApiStatus.HttpErrorType.UNAUTHORIZED, t.message())
                404 -> ApiStatus.Failure(ApiStatus.HttpErrorType.NOT_FOUND, t.message())
                403 -> ApiStatus.Failure(ApiStatus.HttpErrorType.FORBIDDEN, t.message())
                409 -> ApiStatus.Failure(ApiStatus.HttpErrorType.CONFLICT, t.message())
                500 -> ApiStatus.Failure(ApiStatus.HttpErrorType.SERVER_ERROR, t.message())
                else -> ApiStatus.Failure(ApiStatus.HttpErrorType.UNKNOWN_ERROR, t.message())
            }
            is ConnectException, is SocketTimeoutException, is UnknownHostException -> ApiStatus.Failure(
                ApiStatus.HttpErrorType.NETWORK_ERROR,
                t.message.toString()
            )
            else -> ApiStatus.Failure(ApiStatus.HttpErrorType.UNKNOWN_ERROR, "$t")
        }
    }
}