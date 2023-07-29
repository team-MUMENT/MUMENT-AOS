package com.mument_android.data.usecase.mypage

import com.mument_android.domain.entity.mypage.RequestUnregisterEntity
import com.mument_android.domain.repository.mypage.UnregisterRepository
import com.mument_android.domain.usecase.mypage.FetchUnregisterInfoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUnregisterInfoUseCaseImpl @Inject constructor(
    private val unregisterRepository: UnregisterRepository
) : FetchUnregisterInfoUseCase {
    override suspend fun invoke(requestUnregisterEntity: RequestUnregisterEntity): Flow<Boolean> =
        unregisterRepository.fetchUnregisterInfo(requestUnregisterEntity)
}