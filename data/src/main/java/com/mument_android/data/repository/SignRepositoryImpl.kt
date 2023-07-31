package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.GetWebViewMapper
import com.mument_android.data.mapper.sign.KakaoLoginMapper
import com.mument_android.data.mapper.sign.NewTokenMapper
import com.mument_android.data.mapper.sign.SetProfileMapper
import com.mument_android.data.util.MultipartResolver
import com.mument_android.domain.entity.sign.*
import com.mument_android.domain.repository.sign.SignRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val setProfileMapper: SetProfileMapper,
    private val kakaoLoginMapper: KakaoLoginMapper,
    private val getWebViewMapper: GetWebViewMapper,
    private val newTokenMapper: NewTokenMapper,
    private val multipartResolver: MultipartResolver
) : SignRepository {

    override suspend fun signDupCheck(userName: String): Int {
        signDataSource.signDupCheck(userName).let {
            return it.code()
        }
    }

    override suspend fun signSetProfile(
        imageArray: ByteArray, imageType: String, nickName : String
    ): SetProfileEntity? {
        val requestBody = HashMap<String, RequestBody>()
        requestBody[imageType] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        multipartResolver.createImageMultiPart(imageArray)?.let { image ->
            signDataSource.signPutProfile(image, requestBody).let {
                return if (it.data != null) {
                    setProfileMapper.map(it.data)
                } else null
            }
        } ?: return null
    }


    override suspend fun kakaoLogin(requestKakaoData: RequestKakaoData): KakaoEntity? {
        signDataSource.signKakao(kakaoLoginMapper.requestMap(requestKakaoData)).let {
            return if (it.data != null) {
                kakaoLoginMapper.map(it.data)
            } else null
        }
    }

    override suspend fun getViewView(page: String, os: String): WebViewEntity? {
        signDataSource.getWebView(page, os).let {
            return getWebViewMapper.map(it.data)
        }
    }

    override suspend fun newToken(): NewTokenEntity? {
        signDataSource.newToken().let {
            return newTokenMapper.map(it.data)
        }
    }

}