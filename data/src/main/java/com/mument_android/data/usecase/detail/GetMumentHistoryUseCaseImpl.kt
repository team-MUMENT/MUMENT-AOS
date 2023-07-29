package com.mument_android.data.usecase.detail

import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.usecase.detail.GetMumentHistoryUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMumentHistoryUseCaseImpl @Inject constructor(val mumentDetailRepository: MumentDetailRepository) :
    GetMumentHistoryUseCase {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String,
        default: String,
    ): Flow<List<MumentHistory>?> =
        mumentDetailRepository.fetchMumentHistory(userId, musicId, default)
}