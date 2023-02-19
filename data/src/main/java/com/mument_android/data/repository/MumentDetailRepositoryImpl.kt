package com.mument_android.data.repository

import com.mument_android.core.network.ApiStatus
import com.mument_android.data.controller.DeleteMumentController
import com.mument_android.data.datasource.detail.HistoryDataSource
import com.mument_android.data.datasource.detail.MumentDetailDataSource
import com.mument_android.data.datasource.detail.ReportMumentDataSource
import com.mument_android.data.mapper.detail.MumentDetailMapper
import com.mument_android.data.mapper.detail.ReportMumentMapper
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.entity.detail.ReportRequest
import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.util.ApiStatusExtensions.toApiStatus
import com.mument_android.domain.util.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MumentDetailRepositoryImpl @Inject constructor(
    private val mumentDetailDataSource: MumentDetailDataSource,
    private val mumentDetailMapper: MumentDetailMapper,
    private val deleteMumentController: DeleteMumentController,
    private val historyDataSource: HistoryDataSource,
    private val errorHandler: ErrorHandler,
    private val reportMumentDataSource: ReportMumentDataSource,
    private val reportMumentMapper: ReportMumentMapper
) : MumentDetailRepository {
    override suspend fun fetchMumentDetail(mumentId: String): Flow<ApiStatus<MumentDetailEntity>> =
        mumentDetailDataSource.fetchMumentDetail(mumentId)
            .map {
                it.data?.let { dto -> mumentDetailMapper.map(dto) }
                    ?: throw NullPointerException("Can't Receive Data")
            }
            .toApiStatus(errorHandler)

    override suspend fun deleteMument(mumentId: String): Flow<ApiStatus<Unit>> =
        deleteMumentController.deleteMument(mumentId)
            .map { }
            .toApiStatus(errorHandler)

    override suspend fun reportMument(mumentId: String, reportRequest: ReportRequest): Void? {
        reportMumentDataSource.reportMument(mumentId, reportMumentMapper.map(reportRequest)).let {
            return it.data
        }
    }

    override suspend fun fetchMumentHistory(
        userId: String,
        musicId: String,
        default: String
    ): Flow<List<MumentHistory>?> = flow {
        emit(historyDataSource.getMumentHistory(userId, musicId, default))
    }
}