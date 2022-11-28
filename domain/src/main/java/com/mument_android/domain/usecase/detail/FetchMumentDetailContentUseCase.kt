package com.mument_android.domain.usecase.detail

import com.mument_android.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface FetchMumentDetailContentUseCase {
    suspend operator fun invoke(mumentId: String): Flow<MumentDetailEntity>
}