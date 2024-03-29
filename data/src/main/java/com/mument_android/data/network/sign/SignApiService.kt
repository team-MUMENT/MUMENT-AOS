package com.mument_android.data.network.sign

import com.mument_android.data.dto.sign.*
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SignApiService {

    @GET("/user/profile/check/{userName}")
    suspend fun signDuplicationCheck(
        @Path("userName") userName: String
    ): Response<Any?>

    @Multipart
    @PUT("/user/profile")
    suspend fun putProfile(
        @Part image: MultipartBody.Part?,
        @PartMap body: HashMap<String, RequestBody>
    ): BaseResponse<SetProfileDto>


    @POST("/auth/login")
    suspend fun postLogin(
        @Body requestKakaoDto: RequestKakaoDto
    ): BaseResponse<KakaoDto>

    @GET("/user/webview-link")
    suspend fun getWebLink(
        @Query("page") page: String,
        @Query("os") os: String
    ): BaseResponse<WebViewDto>

    @GET("/auth/token")
    suspend fun getNewToken(): BaseResponse<NewTokenDto>

}