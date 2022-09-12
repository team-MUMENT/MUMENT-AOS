package com.startup.domain.usecase.main

import kotlinx.coroutines.flow.Flow


interface CancelLikeMumentUseCase {
    suspend operator fun invoke(mumentId: String, userId: String): Flow<Int>
}