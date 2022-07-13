package com.mument_android.app.data.network.util

sealed class ApiResult<out T>(val data: T?) {
    data class Loading<T>(val datas: T?): ApiResult<T>(datas)
    data class Success<T>(val datas: T): ApiResult<T>(datas)
    data class Failure(val throwable: Throwable?): ApiResult<Nothing>(null)
}