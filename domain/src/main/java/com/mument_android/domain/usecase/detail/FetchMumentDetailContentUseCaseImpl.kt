package com.mument_android.domain.usecase.detail

import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMumentDetailContentUseCaseImpl @Inject constructor(
    private val mumentDetailRepository: MumentDetailRepository
): FetchMumentDetailContentUseCase {
    override suspend operator fun invoke(mumentId: String): Flow<MumentDetailEntity> =
        mumentDetailRepository.fetchMumentDetail(mumentId)
}