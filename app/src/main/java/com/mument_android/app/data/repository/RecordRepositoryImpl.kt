package com.mument_android.app.data.repository

import com.mument_android.app.data.controller.RecordController
import com.mument_android.app.data.controller.RecordModifyController
import com.mument_android.app.data.datasource.record.RecordDataSource
import com.mument_android.app.data.mapper.record.MumentRecordMapper
import com.mument_android.app.data.mapper.record.RecordMapper
import com.startup.domain.entity.record.MumentRecordEntity
import com.startup.domain.entity.record.RecordIsFirstEntity
import com.startup.domain.repository.record.RecordRepository
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
    private val mumentRecordMapper: MumentRecordMapper
) : RecordRepository {
    override suspend fun fetchMumentRecord(
        userId: String,
        musicId: String
    ): Flow<RecordIsFirstEntity> = flow {
        recordDataSource.fetchMumentRecord(userId, musicId)?.let {
            emit(recordMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateMumentRecord(
        mumentId: String,
        entity: MumentRecordEntity
    ): Flow<String> = flow {
        recordModifyController.recordModifyMument(mumentId, mumentRecordMapper.map(entity))
            .data?.let { emit(it.id) }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertMumentRecord(
        musicId: String,
        userId: String,
        entity: MumentRecordEntity
    ): Flow<String> = flow {
        recordController.recordMument(musicId, userId, mumentRecordMapper.map(entity)).data?.let {
            emit(it.id)
        }
    }.flowOn(Dispatchers.IO)
}
