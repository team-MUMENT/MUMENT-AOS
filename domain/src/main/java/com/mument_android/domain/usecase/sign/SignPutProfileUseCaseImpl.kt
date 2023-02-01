package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.SetProfileData
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.repository.sign.SignRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignPutProfileUseCaseImpl @Inject constructor(
    private val signRepository : SignRepository
): SignPutProfileUseCase {
    override suspend fun putProfile(data: SetProfileData): Flow<SetProfileEntity> {
        return signRepository.signSetProfile(data)
    }
}

