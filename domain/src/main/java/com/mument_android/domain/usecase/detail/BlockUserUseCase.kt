package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus

interface BlockUserUseCase {
    suspend operator fun invoke(mumentId: String): ApiStatus<Unit>
}