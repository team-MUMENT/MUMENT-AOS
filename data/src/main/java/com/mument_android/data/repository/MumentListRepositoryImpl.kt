package com.mument_android.data.repository

import com.mument_android.data.datasource.detail.MumentListDataSource
import com.mument_android.data.mapper.detail.MumentSummaryMapper
import com.mument_android.domain.entity.detail.MumentSummaryEntity
import com.mument_android.domain.repository.detail.MumentListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MumentListRepositoryImpl @Inject constructor(
    private val mumentSummaryMapper: MumentSummaryMapper,
    private val mumentListDataSource: MumentListDataSource
) : MumentListRepository {
    override suspend fun fetchMumentList(
        musicId: String,
        default: String
    ): Flow<List<MumentSummaryEntity>> = flow {
        val mumentList = mumentListDataSource.fetchMumentList(musicId, default).data?.mumentList
        if (mumentList != null) {
            emit(mumentList.map { mumentSummaryMapper.map(it) })
        }
    }.flowOn(Dispatchers.IO)
}
