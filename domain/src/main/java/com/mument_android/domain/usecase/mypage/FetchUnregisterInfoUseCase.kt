package com.mument_android.domain.usecase.mypage

import com.mument_android.domain.entity.mypage.UnregisterEntity
import kotlinx.coroutines.flow.Flow

interface FetchUnregisterInfoUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}