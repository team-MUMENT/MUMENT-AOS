package com.mument_android.data.usecase.mypage

import com.mument_android.domain.entity.mypage.RequestUnregisterReasonEntity
import com.mument_android.domain.repository.mypage.UnregisterReasonRepository
import com.mument_android.domain.usecase.mypage.PostUnregisterReasonUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUnregisterReasonUseCaseImpl @Inject constructor(
    private val unregisterReasonRepository: UnregisterReasonRepository
) : PostUnregisterReasonUseCase {
    override suspend fun invoke(
        requestUnregisterReasonEntity: RequestUnregisterReasonEntity
    ): Flow<Boolean> =
        unregisterReasonRepository.postUnregisterReason(requestUnregisterReasonEntity)

}