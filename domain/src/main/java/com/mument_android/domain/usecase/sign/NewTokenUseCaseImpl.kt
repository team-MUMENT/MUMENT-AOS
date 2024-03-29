package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.NewTokenEntity
import com.mument_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class NewTokenUseCaseImpl @Inject constructor(
    private val signRepository : SignRepository
): NewTokenUseCase {
    override suspend fun newToken(): NewTokenEntity? {
        return signRepository.newToken()
    }
}

