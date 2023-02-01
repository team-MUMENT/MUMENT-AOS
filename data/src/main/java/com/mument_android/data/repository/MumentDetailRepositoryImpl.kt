package com.mument_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mument_android.core.network.ApiStatus
import com.mument_android.data.controller.DeleteMumentController
import com.mument_android.data.datasource.detail.HistoryPagingSourceFactory
import com.mument_android.data.datasource.detail.MumentDetailDataSource
import com.mument_android.data.mapper.detail.MumentDetailMapper
import com.mument_android.data.network.detail.HistoryService
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.history.HistoryRequestParams
import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.util.ErrorHandler
import com.mument_android.domain.util.ApiStatusExtensions.toApiStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MumentDetailRepositoryImpl @Inject constructor(
    private val mumentDetailDataSource: MumentDetailDataSource,
    private val mumentDetailMapper: MumentDetailMapper,
    private val deleteMumentController: DeleteMumentController,
    private val historyService: HistoryService,
    private val errorHandler: ErrorHandler,
) : MumentDetailRepository {
    override suspend fun fetchMumentDetail(mumentId: String): Flow<ApiStatus<MumentDetailEntity>> =
        mumentDetailDataSource.fetchMumentDetail(mumentId)
            .map { it.data?.let { it -> mumentDetailMapper.map(it) } ?: throw NullPointerException("Can't Receive Data") }
            .toApiStatus(errorHandler)

    override suspend fun deleteMument(mumentId: String): Flow<ApiStatus<Unit>> =
        deleteMumentController.deleteMument(mumentId)
            .map {  }
            .toApiStatus(errorHandler)

    override suspend fun fetchMumentHistory(mumentHistoryRequestParams: HistoryRequestParams): Flow<PagingData<MumentHistory>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                HistoryPagingSourceFactory(
                    historyService,
                    mumentHistoryRequestParams
                )
            }
        ).flow
}