package com.startup.domain.usecase.detail

import com.startup.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMumentUseCaseImpl @Inject constructor(
    private val mumentDetailRepository: MumentDetailRepository
): DeleteMumentUseCase{
    override suspend fun invoke(mumentId: String): Flow<Unit> = mumentDetailRepository.deleteMument(mumentId)
}