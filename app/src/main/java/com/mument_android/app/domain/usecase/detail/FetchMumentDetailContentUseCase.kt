package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface FetchMumentDetailContentUseCase {
    suspend operator fun invoke(mumentId: String, userId: String): Flow<MumentDetailEntity>
}