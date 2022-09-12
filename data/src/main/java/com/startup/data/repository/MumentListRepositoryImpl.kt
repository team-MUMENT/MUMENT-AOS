package com.startup.data.repository

import com.startup.data.BuildConfig
import com.startup.data.datasource.detail.MumentListDataSource
import com.startup.data.mapper.detail.MumentSummaryMapper
import com.startup.domain.entity.detail.MumentSummaryEntity
import com.startup.domain.repository.detail.MumentListRepository
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
        val mumentList = mumentListDataSource.fetchMumentList(musicId, userId, default).data?.mumentList
        val publicMuments = mumentList?.filter { !it.isPrivate }?.map { mumentSummaryMapper.map(it) }
        val filteredList = mutableListOf<MumentSummaryEntity>()
        mumentList?.filter { it.isPrivate }?.onEach {
            if(it.user.id == BuildConfig.USER_ID)  filteredList.add(mumentSummaryMapper.map(it))
        }
        publicMuments?.let { filteredList.addAll(it) }
        emit(filteredList)
    }.flowOn(Dispatchers.IO)
}
