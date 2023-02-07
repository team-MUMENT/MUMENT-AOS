package com.mument_android.core.network

sealed class ApiStatus<out T> {
    object Loading: ApiStatus<Nothing>()
    data class Success<out T>(val data: T) : ApiStatus<T>()
    data class Failure(val code: ErrorMessage, val message: String?) : ApiStatus<Nothing>()
}