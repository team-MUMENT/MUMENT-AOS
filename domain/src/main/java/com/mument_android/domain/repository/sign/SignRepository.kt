package com.mument_android.domain.repository.sign

import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.NewTokenEntity
import com.mument_android.domain.entity.sign.RequestKakaoData
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.entity.sign.WebViewEntity


interface SignRepository {
    suspend fun signDupCheck(userName: String): Int

    suspend fun signSetProfile(
        imageArray: ByteArray, imageType: String, nickName: String
    ): SetProfileEntity?


    suspend fun kakaoLogin(
        requestKakaoData: RequestKakaoData
    ): KakaoEntity?

    suspend fun getViewView(
        page: String, os: String
    ): WebViewEntity?

    suspend fun newToken(): NewTokenEntity?
}