package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.network.util.ApiResult
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MumentDetailRepositoryImpl @Inject constructor(
    private val mumentDetailDataSource: MumentDetailDataSource,
    private val mumentDetailMapper: MumentDetailMapper
): MumentDetailRepository {
    override suspend fun fetchMumentDetail(mumentId: Int): Flow<ApiResult<MumentDetailEntity>> = flow {
        emit(ApiResult.Loading<MumentDetailEntity>(null))
        val response = mumentDetailDataSource.fetchMumentDetail(mumentId)
        emit(ApiResult.Success(mumentDetailMapper.map(response)))
    }.catch { e ->
        emit(ApiResult.Failure(e))
    }.flowOn(Dispatchers.IO)
}