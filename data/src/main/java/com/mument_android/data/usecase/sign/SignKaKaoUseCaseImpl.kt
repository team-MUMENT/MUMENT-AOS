package com.mument_android.data.usecase.sign

import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData
import com.mument_android.domain.repository.sign.SignRepository
import com.mument_android.domain.usecase.sign.SignKaKaoUseCase
import javax.inject.Inject

class SignKaKaoUseCaseImpl @Inject constructor(
    private val signRepository: SignRepository
) : SignKaKaoUseCase {

    override suspend fun kakaoLogin(requestKakaoData: RequestKakaoData): KakaoEntity? {
        return signRepository.kakaoLogin(requestKakaoData)
    }
}

