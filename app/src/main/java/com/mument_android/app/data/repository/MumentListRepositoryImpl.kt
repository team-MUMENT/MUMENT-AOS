package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.detail.MumentListDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.mapper.detail.MumentSummaryDtoMapper
import com.mument_android.app.data.mapper.detail.MumentSummaryMapper
import com.mument_android.app.domain.entity.detail.MumentDetailEntity
import com.mument_android.app.domain.entity.detail.MumentSummaryEntity
import com.mument_android.app.domain.repository.detail.MumentListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MumentListRepositoryImpl @Inject constructor(
    private val mumentSummaryMapper: MumentSummaryMapper,
    private val mumentListDataSource: MumentListDataSource
): MumentListRepository {
    override suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): Flow<List<MumentSummaryEntity>> = flow {
        val mumentList = mumentListDataSource.fetchMumentList(musicId, userId, default).data.mumentList.map {
            mumentSummaryMapper.map(it)
        }
        emit(mumentList)
    }.flowOn(Dispatchers.IO)
}