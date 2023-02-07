package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import kotlinx.coroutines.flow.Flow

interface BlockUserUseCase {
    suspend operator fun invoke(mumentId: String): Flow<ApiStatus<Unit>>
}