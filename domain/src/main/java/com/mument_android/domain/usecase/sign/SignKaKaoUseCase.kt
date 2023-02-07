package com.mument_android.domain.usecase.sign

import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData

interface SignKaKaoUseCase {
    suspend fun kakaoLogin(requestKakaoData: RequestKakaoData) : KakaoEntity?
}