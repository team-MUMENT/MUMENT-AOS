package com.mument_android.app.data.network.util

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Failure(val exception: Exception): ApiResult<Nothing>()
}