package com.startup.domain.usecase.detail

import com.startup.domain.entity.detail.MumentDetailEntity
import kotlinx.coroutines.flow.Flow

interface FetchMumentDetailContentUseCase {
    suspend operator fun invoke(mumentId: String, userId: String): Flow<MumentDetailEntity?>
}