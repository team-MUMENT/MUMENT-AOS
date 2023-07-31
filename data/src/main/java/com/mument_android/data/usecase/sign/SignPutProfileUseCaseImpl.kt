package com.mument_android.data.usecase.sign

import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.repository.sign.SignRepository
import com.mument_android.domain.usecase.sign.SignPutProfileUseCase
import javax.inject.Inject

class SignPutProfileUseCaseImpl @Inject constructor(
    private val signRepository: SignRepository
) : SignPutProfileUseCase {
    override suspend fun invoke(
        imageArray: ByteArray, imageType: String, nickName:String
    ): SetProfileEntity? {
        return signRepository.signSetProfile(imageArray, imageType, nickName)
    }
}

