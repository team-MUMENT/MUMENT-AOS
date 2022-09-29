package com.mument_android.domain.usecase.locker

import com.mument_android.domain.entity.locker.LockerMumentEntity
import kotlinx.coroutines.flow.Flow

interface FetchMyLikeListUseCase {
    suspend operator fun invoke(userId: String, tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>>
}