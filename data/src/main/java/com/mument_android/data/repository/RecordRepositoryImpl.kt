package com.mument_android.data.repository

import com.mument_android.data.controller.RecordController
import com.mument_android.data.controller.RecordModifyController
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.mapper.record.MumentModifyMapper
import com.mument_android.data.mapper.record.MumentRecordMapper
import com.mument_android.data.mapper.record.RecordMapper
import com.mument_android.domain.entity.record.MumentModifyEntity
import com.mument_android.domain.entity.record.MumentRecordEntity
import com.mument_android.domain.entity.record.RecordIsFirstEntity
import com.mument_android.domain.repository.record.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource,
    private val recordMapper: RecordMapper,
    private val recordModifyController: RecordModifyController,
    private val recordController: RecordController,
    private val mumentRecordMapper: MumentRecordMapper,
    private val mumnetModifyMapper: MumentModifyMapper
) : RecordRepository {
    override suspend fun fetchMumentRecord(
        musicId: String
    ): Flow<RecordIsFirstEntity> = flow {
        recordDataSource.fetchMumentRecord(musicId)?.let {
            emit(recordMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateMumentRecord(
        mumentId: String,
        entity: MumentModifyEntity
    ): Flow<String> = flow {
        recordModifyController.recordModifyMument(mumentId, mumnetModifyMapper.map(entity))
            .data?.let { emit(it.id) }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertMumentRecord(
        musicId: String,
        entity: MumentRecordEntity
    ): Flow<Pair<String, Int>> = flow {
        recordController.recordMument(musicId, mumentRecordMapper.map(entity)).data?.let {
            emit(it.id to it.count)
        }
    }.flowOn(Dispatchers.IO)
}
