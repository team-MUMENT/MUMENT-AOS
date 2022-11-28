package com.mument_android.data.repository

import com.mument_android.data.controller.DeleteMumentController
import com.mument_android.data.datasource.detail.MumentDetailDataSource
import com.mument_android.data.mapper.detail.MumentDetailMapper
import com.mument_android.domain.entity.detail.MumentDetailEntity
import com.mument_android.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MumentDetailRepositoryImpl @Inject constructor(
    private val mumentDetailDataSource: MumentDetailDataSource,
    private val mumentDetailMapper: MumentDetailMapper,
    private val deleteMumentController: DeleteMumentController
) : MumentDetailRepository {
    override suspend fun fetchMumentDetail(mumentId: String): Flow<MumentDetailEntity> =
        mumentDetailDataSource.fetchMumentDetail(mumentId)
            .map { mumentDetailMapper.map(it) }
            .flowOn(Dispatchers.Default)

    override suspend fun deleteMument(mumentId: String): Flow<Unit> =
        deleteMumentController.deleteMument(mumentId)
}