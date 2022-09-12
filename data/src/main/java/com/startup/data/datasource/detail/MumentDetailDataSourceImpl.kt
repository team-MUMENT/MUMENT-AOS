package com.startup.data.datasource.detail

import com.startup.data.dto.detail.MumentDetailDto
import com.startup.data.network.detail.DetailApiService
import com.startup.core.base.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MumentDetailDataSourceImpl @Inject constructor(
    private val mumentDetailApiService: DetailApiService
): MumentDetailDataSource {
    override suspend fun fetchMumentDetail(mumentId: String, userId: String): Flow<BaseResponse<MumentDetailDto>> = flow {
        emit(mumentDetailApiService.fetchMumentDetail(mumentId, userId))
    }.flowOn(Dispatchers.IO)

}