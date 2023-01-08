package com.mument_android.core.network

import java.net.ConnectException

sealed class ApiResponse<out T> {
    data class OnSuccess<T>(val body: T): ApiResponse<T>()
    data class OnFailure(val message: String): ApiResponse<Any>()
    object UnUsableNetwork: ApiResponse<Nothing>()

    suspend inline fun <T> verify(crossinline apiCall: suspend () -> ApiResponse<T>): ApiResponse<T>? {
        return kotlin.runCatching {
            apiCall()
        }.onSuccess {
            OnSuccess(it).body
        }.onFailure {
            if (it is ConnectException) {
                UnUsableNetwork
            }
            OnFailure(it.message ?: "")
        }.getOrNull()
    }
}

