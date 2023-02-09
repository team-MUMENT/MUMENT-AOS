package com.mument_android.domain.repository.detail

import androidx.paging.PagingData
import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.detail.ReportRequest
import com.mument_android.domain.entity.history.HistoryRequestParams
import com.mument_android.domain.entity.history.MumentHistory
import kotlinx.coroutines.flow.Flow

interface MumentDetailRepository {
    suspend fun fetchMumentDetail(mumentId: String): Flow<ApiStatus<MumentDetailEntity>>
    suspend fun fetchMumentHistory(mumentHistoryRequestParams: HistoryRequestParams): Flow<PagingData<MumentHistory>>
    suspend fun deleteMument(mumentId: String):Flow<ApiStatus<Unit>>
    suspend fun reportMument(mumentId: String, reportRequest: ReportRequest): Void?
}