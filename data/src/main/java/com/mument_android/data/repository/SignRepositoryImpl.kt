package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.*
import com.mument_android.data.mapper.sign.GetWebViewMapper
import com.mument_android.data.mapper.sign.RequestSetProfileMapper
import com.mument_android.data.mapper.sign.SetProfileMapper
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.domain.entity.sign.*
import com.mument_android.domain.repository.sign.SignRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val setProfileMapper: SetProfileMapper,
    private val kakaoLoginMapper: KakaoLoginMapper,
    private val requestSetProfileMapper: RequestSetProfileMapper,
    private val getWebViewMapper: GetWebViewMapper,
    private val newTokenMapper: NewTokenMapper

): SignRepository {

    override suspend fun signDupCheck(userName: String) : Int {
        signDataSource.signDupCheck(userName).let {
           return it.code()
        }
    }

    override suspend fun signSetProfile(
        image: MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ): SetProfileEntity {
        signDataSource.signPutProfile(image,body).let {
            return setProfileMapper.map(it.data!!)
        }
    }


    override suspend fun kakaoLogin(requestKakaoData: RequestKakaoData): KakaoEntity? {
        signDataSource.signKakao(kakaoLoginMapper.requestMap(requestKakaoData)).let {
            return kakaoLoginMapper.map(it.data)
        }
    }

    override suspend fun getViewView(page: String): WebViewEntity? {
        signDataSource.getWebView(page).let {
            return getWebViewMapper.map(it.data)
        }
    }

    override suspend fun newToken(): NewTokenEntity? {
        signDataSource.newToken().let {
            return newTokenMapper.map(it.data)
        }
    }

}