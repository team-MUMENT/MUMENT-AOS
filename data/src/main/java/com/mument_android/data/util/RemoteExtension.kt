package com.mument_android.data.util

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend inline fun <T> catchingApiCall(crossinline apiCall: suspend () -> Response<T>): ResultWrapper<T?> {
    return runCatching {
        ResultWrapper.Success(apiCall().body())
    }.getOrElse { throwable ->
        when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> {
                val code = throwable.code()
                val message = throwable.message
                ResultWrapper.GenericError(code, message)
            }
            else -> ResultWrapper.GenericError(null, null)
        }
    }
}