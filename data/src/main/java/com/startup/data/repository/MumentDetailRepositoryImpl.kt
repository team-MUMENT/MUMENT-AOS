package com.startup.data.repository

import com.startup.data.controller.DeleteMumentController
import com.startup.data.datasource.detail.MumentDetailDataSource
import com.startup.data.mapper.detail.MumentDetailMapper
import com.startup.domain.entity.detail.MumentDetailEntity
import com.startup.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MumentDetailRepositoryImpl @Inject constructor(
    private val mumentDetailDataSource: MumentDetailDataSource,
    private val mumentDetailMapper: MumentDetailMapper,
    private val deleteMumentController: DeleteMumentController
) : MumentDetailRepository {
    override suspend fun fetchMumentDetail(
        mumentId: String,
        userId: String
    ): Flow<MumentDetailEntity?> =
        mumentDetailDataSource.fetchMumentDetail(mumentId, userId)
            .map { it.data?.let { mumentDetailMapper.map(it) } }
            .flowOn(Dispatchers.Default)
            .catch { //Todo exception handling
            }

    override suspend fun deleteMument(mumentId: String): Flow<Unit> =
        deleteMumentController.deleteMument(mumentId)


}