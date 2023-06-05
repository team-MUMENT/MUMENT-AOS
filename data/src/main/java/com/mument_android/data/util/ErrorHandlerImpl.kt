package com.mument_android.data.util

import com.mument_android.core.network.ErrorMessage
import com.mument_android.domain.util.ErrorHandler
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {
    override fun handleError(t: Throwable): ErrorMessage =
        when (t) {
            is HttpException -> when (t.code()) {
                400 -> ErrorMessage.INVALID
                401 -> ErrorMessage.UNAUTHORIZED
                404 -> ErrorMessage.NOT_FOUND
                403 -> ErrorMessage.FORBIDDEN
                409 -> ErrorMessage.CONFLICT
                500 -> ErrorMessage.SERVER_ERROR
                else -> ErrorMessage.UNKNOWN_ERROR
            }
            is ConnectException, is SocketTimeoutException, is UnknownHostException -> ErrorMessage.NETWORK_ERROR
            else -> ErrorMessage.UNKNOWN_ERROR
        }
}