package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMumentDetailContentUseCaseImpl @Inject constructor(
    private val mumentDetailRepository: MumentDetailRepository
): FetchMumentDetailContentUseCase {
    override suspend operator fun invoke(mumentId: Int, userId: Int): Flow<ApiResult<MumentDetailEntity>> =
        mumentDetailRepository.fetchMumentDetail(mumentId, userId)
}