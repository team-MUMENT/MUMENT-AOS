package com.mument_android.domain.usecase.main

import kotlinx.coroutines.flow.Flow


interface CancelLikeMumentUseCase {
    suspend operator fun invoke(mumentId: String): Flow<Int>
}