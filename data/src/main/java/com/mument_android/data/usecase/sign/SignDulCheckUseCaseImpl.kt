package com.mument_android.data.usecase.sign

import com.mument_android.domain.repository.sign.SignRepository
import com.mument_android.domain.usecase.sign.SignDulCheckUseCase
import javax.inject.Inject

class SignDulCheckUseCaseImpl @Inject constructor(
    private val signRepository: SignRepository
) : SignDulCheckUseCase {
    override suspend fun dupCheckNickname(userName: String): Int {
        return signRepository.signDupCheck(userName)
    }
}

