package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.repository.sign.SignRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SignPutProfileUseCaseImpl @Inject constructor(
    private val signRepository : SignRepository
): SignPutProfileUseCase {
    override suspend fun invoke(
        image: MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ): SetProfileEntity {
        return signRepository.signSetProfile(image, body)
    }

}

