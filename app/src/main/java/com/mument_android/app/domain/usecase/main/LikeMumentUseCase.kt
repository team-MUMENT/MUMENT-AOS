package com.mument_android.app.domain.usecase.main

import com.mument_android.app.data.network.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface LikeMumentUseCase {
    suspend operator fun invoke(mumentId: String, userId: String): Flow<Int>
}