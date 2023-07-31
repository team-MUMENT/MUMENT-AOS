package com.mument_android.data.usecase.locker

import com.mument_android.domain.entity.locker.LockerMumentEntity
import com.mument_android.domain.repository.locker.LockerRepository
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMyMumentListUseCaseImpl @Inject constructor(
    private val lockerRepository: LockerRepository
) : FetchMyMumentListUseCase {
    override suspend fun invoke(tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>> {
        return lockerRepository.fetchLockerMumentList(tag1, tag2, tag3)
    }

}

