package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.mapper.locker.LockerMapper
import com.mument_android.app.domain.entity.LockerMumentEntity
import com.mument_android.app.domain.repository.locker.LockerRepository
import javax.inject.Inject

class LockerRepositoryImpl @Inject constructor(
    private val lockerMapper: LockerMapper,
    private val lockerDataSource: LockerDataSource
): LockerRepository {
    override suspend fun fetchLockerMumentList(): List<LockerMumentEntity> =
        lockerMapper.map(lockerDataSource.fetchLockerMumumentList())
}