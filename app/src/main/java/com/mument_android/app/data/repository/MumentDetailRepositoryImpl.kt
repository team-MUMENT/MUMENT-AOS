package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MumentDetailRepositoryImpl @Inject constructor(
    private val mumentDetailDataSource: MumentDetailDataSource,
    private val mumentDetailMapper: MumentDetailMapper
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
}