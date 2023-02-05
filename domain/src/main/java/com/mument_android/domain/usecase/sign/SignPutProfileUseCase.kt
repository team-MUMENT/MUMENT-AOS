package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.SetProfileEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SignPutProfileUseCase {
    suspend operator fun invoke(image: MultipartBody.Part?, body: HashMap<String, RequestBody>) : SetProfileEntity

}