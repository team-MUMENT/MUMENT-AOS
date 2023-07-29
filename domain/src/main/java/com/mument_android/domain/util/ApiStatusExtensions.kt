package com.mument_android.domain.util

import com.mument_android.core.network.ApiStatus
import com.mument_android.core.network.ErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

object ApiStatusExtensions {
    suspend inline fun <T> Flow<T>.toApiStatus(
        handler: ErrorHandler
    ): Flow<ApiStatus<T>> = this.map {
        ApiStatus.Success(it) as ApiStatus<T>
    }.catch { throwable ->
        throwable.cause?.let { t ->
            handler.handleError(t).let { errorMessage ->
                emit(ApiStatus.Failure(code = errorMessage, message = errorMessage.name))
            }
        } ?: emit(ApiStatus.Failure(ErrorMessage.UNKNOWN_ERROR, "Unknown"))
    }.onStart {
        emit(ApiStatus.Loading)
    }
}
