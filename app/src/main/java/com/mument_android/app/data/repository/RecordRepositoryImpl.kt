package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.record.RecordDataSource
import com.mument_android.app.data.mapper.record.RecordMapper
import com.mument_android.app.domain.entity.record.RecordIsFirstEntity
import com.mument_android.app.domain.repository.record.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource,
    private val recordMapper: RecordMapper
) : RecordRepository {
    override suspend fun fetchMumentRecord(
        userId: String,
        musicId: String
    ): Flow<RecordIsFirstEntity> = flow {
        recordDataSource.fetchMumentRecord(userId, musicId)?.let {
            emit(recordMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)
}
