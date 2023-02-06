package com.mument_android.domain.repository.sign

import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.entity.sign.WebViewEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface SignRepository {
    suspend fun signDupCheck(userName: String) : Int

    suspend fun signSetProfile(
        image: MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ) : SetProfileEntity

    suspend fun getViewView(
        page: String
    ) : WebViewEntity?
}