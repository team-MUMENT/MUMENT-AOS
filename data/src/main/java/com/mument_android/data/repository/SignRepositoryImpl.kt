package com.mument_android.data.repository

import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.sign.GetWebViewMapper
import com.mument_android.data.mapper.sign.RequestSetProfileMapper
import com.mument_android.data.mapper.sign.SetProfileMapper
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.domain.entity.sign.SetProfileEntity
import com.mument_android.domain.entity.sign.WebViewEntity
import com.mument_android.domain.repository.sign.SignRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource,
    private val signMapper: SignMapper,
    private val setProfileMapper: SetProfileMapper,
    private val requestSetProfileMapper: RequestSetProfileMapper,
    private val getWebViewMapper: GetWebViewMapper
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

    override suspend fun getViewView(page: String): WebViewEntity? {
        signDataSource.getWebView(page).let {
            return getWebViewMapper.map(it.data)
        }
    }

}