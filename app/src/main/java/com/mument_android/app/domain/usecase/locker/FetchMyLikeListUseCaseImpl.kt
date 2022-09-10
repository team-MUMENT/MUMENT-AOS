package com.mument_android.app.domain.usecase.locker

import com.mument_android.app.domain.entity.locker.LockerMumentEntity
import com.mument_android.app.domain.repository.locker.LockerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class FetchMyLikeListUseCaseImpl @Inject constructor(
    private val lockerRepository: LockerRepository
): FetchMyLikeListUseCase {
    override suspend fun invoke(userId: String, tag1: Int?, tag2: Int?, tag3: Int?): Flow<List<LockerMumentEntity>> {
                return lockerRepository.fetchLockerLikeList(userId, tag1, tag2, tag3)
    }

}

