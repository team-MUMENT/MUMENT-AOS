package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.SetProfileEntity

interface SignPutProfileUseCase {
    suspend operator fun invoke(imageArray: ByteArray, imageType: String, nickName: String): SetProfileEntity?

}