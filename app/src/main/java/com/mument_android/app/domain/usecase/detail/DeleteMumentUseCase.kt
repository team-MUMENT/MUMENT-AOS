package com.mument_android.app.domain.usecase.detail

import kotlinx.coroutines.flow.Flow

interface DeleteMumentUseCase {
    suspend operator fun invoke(mumentId: String): Flow<Unit>
}