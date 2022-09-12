package com.mument_android.app.data.repository

import com.mument_android.app.data.controller.DeleteMumentController
import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
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