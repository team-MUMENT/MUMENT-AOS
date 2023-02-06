package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.UnregisterReasonEntity
import kotlinx.coroutines.flow.Flow

interface PostUnregisterReasonUseCase {
    suspend operator fun invoke(
        leaveCategoryId: Int,
        reasonEtc: String
    ): Flow<UnregisterReasonEntity>
}