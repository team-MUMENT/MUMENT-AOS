package com.mument_android.domain.usecase.locker

import com.mument_android.domain.entity.locker.LockerMumentEntity
import kotlinx.coroutines.flow.Flow

interface FetchMyMumentListUseCase {
    suspend operator fun invoke(tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>>
}