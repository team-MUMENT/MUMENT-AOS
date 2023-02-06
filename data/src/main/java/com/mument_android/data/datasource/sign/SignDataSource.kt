package com.mument_android.data.datasource.sign

import com.mument_android.data.dto.sign.KakaoDto
import com.mument_android.data.dto.sign.RequestKakaoDto
import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.dto.sign.WebViewDto
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

<<<<<<< HEAD
    suspend fun signKakao(requestKakaoDto: RequestKakaoDto) : BaseResponse<KakaoDto>
=======
    suspend fun getWebView(page: String) : BaseResponse<WebViewDto>
>>>>>>> 77f8ea162ec61c1d4d5ae642ce91ac023a6855fe
}