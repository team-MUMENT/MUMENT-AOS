package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.*
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


interface SignDataSource {
    suspend fun signDupCheck(userName: String) : Response<Any?>

    suspend fun signPutProfile(
        image : MultipartBody.Part?,
        body: HashMap<String, RequestBody>
    ) : BaseResponse<SetProfileDto>

    suspend fun signKakao(requestKakaoDto: RequestKakaoDto) : BaseResponse<KakaoDto>

    suspend fun getWebView(page: String) : BaseResponse<WebViewDto>

    suspend fun newToken(): BaseResponse<NewTokenDto>
}