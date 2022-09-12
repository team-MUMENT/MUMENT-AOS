package com.startup.domain.usecase.detail

import com.startup.domain.entity.detail.MumentDetailEntity
import com.startup.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMumentDetailContentUseCaseImpl @Inject constructor(
    private val mumentDetailRepository: MumentDetailRepository
): FetchMumentDetailContentUseCase {
    override suspend operator fun invoke(mumentId: String, userId: String): Flow<MumentDetailEntity?> =
        mumentDetailRepository.fetchMumentDetail(mumentId, userId)
}