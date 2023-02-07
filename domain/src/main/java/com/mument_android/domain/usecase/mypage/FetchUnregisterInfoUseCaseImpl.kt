package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.UnregisterEntity
import com.mument_android.domain.repository.mypage.UnregisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUnregisterInfoUseCaseImpl @Inject constructor(
    private val unregisterRepository: UnregisterRepository
) : FetchUnregisterInfoUseCase {
    override suspend fun invoke(): Flow<Boolean> =
        unregisterRepository.fetchUnregisterInfo()
}