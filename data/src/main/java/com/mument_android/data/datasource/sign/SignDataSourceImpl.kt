package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.data.dto.sign.RequestKakaoDto
import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.dto.sign.WebViewDto
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
        return signApiService.putProfile(image,body)
    }

<<<<<<< HEAD
    override suspend fun signKakao(requestKakaoDto: RequestKakaoDto): BaseResponse<KakaoDto> {
        return signApiService.postLogin(requestKakaoDto)
=======
    override suspend fun getWebView(page: String): BaseResponse<WebViewDto> {
        return signApiService.getWebLink(page)
>>>>>>> 77f8ea162ec61c1d4d5ae642ce91ac023a6855fe
    }

}