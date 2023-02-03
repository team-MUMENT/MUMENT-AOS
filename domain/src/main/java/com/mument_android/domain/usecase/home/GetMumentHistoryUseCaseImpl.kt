package com.mument_android.domain.usecase.home

import androidx.paging.PagingData
import com.mument_android.domain.entity.history.HistoryRequestParams
import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMumentHistoryUseCaseImpl @Inject constructor(val mumentDetailRepository: MumentDetailRepository) :
    GetMumentHistoryUseCase {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String,
        default: String,
    ): Flow<PagingData<MumentHistory>> =
        mumentDetailRepository.fetchMumentHistory(HistoryRequestParams(userId, musicId, default))
}