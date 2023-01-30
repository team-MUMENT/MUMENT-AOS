package com.mument_android.domain.repository.locker

import com.mument_android.domain.entity.locker.LockerMumentEntity
import kotlinx.coroutines.flow.Flow

interface LockerRepository {
    suspend fun fetchLockerMumentList(tag1 : Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>>

    suspend fun fetchLockerLikeList(tag1: Int?, tag2: Int?, tag3: Int?) : Flow<List<LockerMumentEntity>>

}