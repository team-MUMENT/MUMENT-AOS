package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.*
import com.mument_android.domain.entity.sign.KakaoEntity
import com.mument_android.domain.entity.sign.RequestKakaoData
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.repository.sign.SignRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val setProfileMapper: SetProfileMapper,
    private val kakaoLoginMapper: KakaoLoginMapper,
    private val requestKakaoLoginMapper: RequestKakaoLoginMapper
): SignRepository {

    override suspend fun signDupCheck(profileId: String) : Int {
        signDataSource.signDupCheck(profileId).let {
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

    override suspend fun kakaoLogin(requestKakaoData: RequestKakaoData): KakaoEntity {
        signDataSource.signKakao(requestKakaoLoginMapper.map(requestKakaoData)).let {
            return kakaoLoginMapper.map(it.data!!)
        }
    }

}