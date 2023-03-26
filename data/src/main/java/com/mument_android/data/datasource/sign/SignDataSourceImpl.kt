package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.*
import com.mument_android.data.network.sign.SignApiService
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class SignDataSourceImpl @Inject constructor(
    private val signApiService: SignApiService
) : SignDataSource {
    override suspend fun signDupCheck(userName: String): Response<Any?> {
        return signApiService.signDuplicationCheck(userName)
    }

    override suspend fun signPutProfile(
        image: MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ): BaseResponse<SetProfileDto> {
        return signApiService.putProfile(image, body)
    }

    override suspend fun signKakao(requestKakaoDto: RequestKakaoDto): BaseResponse<KakaoDto> {
        return signApiService.postLogin(requestKakaoDto)
    }

    override suspend fun getWebView(page: String, os: String): BaseResponse<WebViewDto> {
        return signApiService.getWebLink(page, os)
    }

    override suspend fun newToken(): BaseResponse<NewTokenDto> {
        return signApiService.getNewToken()
    }

}