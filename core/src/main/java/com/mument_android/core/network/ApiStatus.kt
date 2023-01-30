package com.mument_android.core.network

sealed class ApiStatus<out T> {
    data class Success<out T>(val data: T?) : ApiStatus<T>()
    data class Failure(val code: HttpErrorType, val message: String?) : ApiStatus<Nothing>()

    enum class HttpErrorType {
        INVALID,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        UNKNOWN_ERROR,
        SERVER_ERROR,
        NETWORK_ERROR
    }
}