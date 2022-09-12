package com.startup.domain.usecase.locker

import com.startup.domain.entity.locker.LockerMumentEntity
import kotlinx.coroutines.flow.Flow

interface FetchMyMumentListUseCase {
    suspend operator fun invoke(userId: String, tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>>
}