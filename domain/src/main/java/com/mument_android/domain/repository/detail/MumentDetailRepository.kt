package com.mument_android.domain.repository.detail

import androidx.paging.PagingData
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.history.HistoryRequestParams
import com.mument_android.domain.entity.history.MumentHistory
import kotlinx.coroutines.flow.Flow

interface MumentDetailRepository {
    suspend fun fetchMumentDetail(mumentId: String): Flow<MumentDetailEntity?>
    suspend fun deleteMument(mumentId: String): Flow<Unit>
    suspend fun fetchMumentHistory(mumentHistoryRequestParams: HistoryRequestParams): Flow<PagingData<MumentHistory>>
}