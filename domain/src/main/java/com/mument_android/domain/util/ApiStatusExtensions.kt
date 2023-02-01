package com.mument_android.domain.util

import com.mument_android.core.network.ApiStatus
import kotlinx.coroutines.flow.*

object ApiStatusExtensions {
    suspend inline fun <T> Flow<T>.toApiStatus(
        handler: ErrorHandler
    ): Flow<ApiStatus<T>> = this.map {
        ApiStatus.Success(it) as ApiStatus<T>
    }.catch {
        emit(ApiStatus.Failure(handler.handleError(it.cause!!), it.cause?.message))
    }.onStart {
        emit(ApiStatus.Loading)
    }
}
