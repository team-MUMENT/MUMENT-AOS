package com.mument_android.domain.repository.sign

import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData
import com.mument_android.domain.entity.sign.SetProfileEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface SignRepository {
    suspend fun signDupCheck(profileId: String) : Int

    suspend fun signSetProfile(
        image: MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ) : SetProfileEntity

    suspend fun kakaoLogin(
        requestKakaoData : RequestKakaoData
    ) : KakaoEntity
}