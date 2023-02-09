package com.mument_android.domain.util

import android.util.Log
import com.mument_android.core.network.ApiStatus
import com.mument_android.core.network.ErrorMessage
import kotlinx.coroutines.flow.*

object ApiStatusExtensions {
    suspend inline fun <T> Flow<T>.toApiStatus(
        handler: ErrorHandler
    ): Flow<ApiStatus<T>> = this.map {
        ApiStatus.Success(it) as ApiStatus<T>
    }.catch {
        it.cause?.let { t ->
            handler.handleError(t).let {
                emit(ApiStatus.Failure(it, it.name))
            }
        } ?: emit(ApiStatus.Failure(ErrorMessage.UNKNOWN_ERROR, "Unknown"))
    }.onStart {
        emit(ApiStatus.Loading)
    }
}
