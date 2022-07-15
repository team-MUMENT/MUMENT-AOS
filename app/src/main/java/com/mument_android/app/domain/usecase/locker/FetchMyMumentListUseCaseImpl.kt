package com.mument_android.app.domain.usecase.locker

import com.mument_android.app.domain.entity.LockerMumentEntity
import com.mument_android.app.domain.repository.locker.LockerRepository
import javax.inject.Inject

class FetchMyMumentListUseCaseImpl @Inject constructor(
    private val lockerRepository: LockerRepository
): FetchMyMumentListUseCase {
    override suspend fun invoke(): List<LockerMumentEntity> =
        lockerRepository.fetchLockerMumentList()
}