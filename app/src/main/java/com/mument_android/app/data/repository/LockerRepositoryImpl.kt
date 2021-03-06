package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.mapper.locker.LockerMapper
import com.mument_android.app.domain.entity.locker.LockerMumentEntity
import com.mument_android.app.domain.repository.locker.LockerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class LockerRepositoryImpl @Inject constructor(
    private val lockerMapper: LockerMapper,
    private val lockerDataSource: LockerDataSource
): LockerRepository {
    override suspend fun fetchLockerMumentList(userId: String, tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>> = flow {
        lockerDataSource.fetchLockerMumumentList(userId, tag1, tag2,tag3).data?.let {
            emit(lockerMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun fetchLockerLikeList(userId: String, tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>> = flow {
        lockerDataSource.fetchLockerLikeList(userId, tag1, tag2, tag3).data?.let { emit(lockerMapper.map(it)) }
    }.flowOn(Dispatchers.IO)
}