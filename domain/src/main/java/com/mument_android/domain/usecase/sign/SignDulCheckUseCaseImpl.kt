package com.mument_android.domain.usecase.sign

import com.mument_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class SignDulCheckUseCaseImpl @Inject constructor(
    private val signRepository : SignRepository
): SignDulCheckUseCase {
    override suspend fun dupCheckNickname(userId: String): Int {
        return signRepository.signDupCheck(userId)
    }
}

