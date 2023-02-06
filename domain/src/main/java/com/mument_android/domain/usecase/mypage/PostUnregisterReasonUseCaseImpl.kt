package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity
import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import com.mument_android.domain.repository.mypage.UnregisterReasonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUnregisterReasonUseCaseImpl @Inject constructor(
    private val unregisterReasonRepository: UnregisterReasonRepository
) : PostUnregisterReasonUseCase {
    override suspend fun invoke(
        requestUnregisterReasonEntity: RequestUnregisterReasonEntity
    ): Flow<UnregisterReasonEntity> {
        return unregisterReasonRepository.postUnregisterReason(requestUnregisterReasonEntity)
    }
}