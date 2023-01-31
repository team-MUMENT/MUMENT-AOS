package com.mument_android.domain.usecase.mypage

import kotlinx.coroutines.flow.Flow

interface DeleteBlockUserUseCase {
    suspend operator fun invoke(blockedUserId: String): Flow<Unit>
}