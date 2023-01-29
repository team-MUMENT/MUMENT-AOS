package com.mument_android.domain.repository.detail

import com.mument_android.core.network.ApiStatus

interface BlockUserRepository {
    suspend fun block(mumentId: String): ApiStatus<Unit>
}