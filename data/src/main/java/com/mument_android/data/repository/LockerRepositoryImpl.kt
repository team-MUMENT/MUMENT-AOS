package com.mument_android.data.repository

import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.mapper.locker.LockerMapper
import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.domain.repository.locker.LockerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LockerRepositoryImpl @Inject constructor(
    private val lockerMapper: LockerMapper,
    private val lockerDataSource: LockerDataSource
): LockerRepository {
    override suspend fun fetchLockerMumentList( tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>> = flow {
        lockerDataSource.fetchLockerMumumentList(tag1, tag2,tag3).data?.let {
            emit(lockerMapper.map(it))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun fetchLockerLikeList(tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>> = flow {
        lockerDataSource.fetchLockerLikeList(tag1, tag2, tag3).data?.let { emit(lockerMapper.map(it)) }
    }.flowOn(Dispatchers.IO)
}