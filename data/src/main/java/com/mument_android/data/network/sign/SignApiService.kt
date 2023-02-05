package com.mument_android.data.network.sign

import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SignApiService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzAsInByb2ZpbGVJZCI6IuyViOuTnO2FjOyKpO2KuOyaqSIsImltYWdlIjpudWxsLCJpYXQiOjE2NzMxMjYzNzgsImV4cCI6MTY3NTcxODM3OCwiaXNzIjoiTXVtZW50In0.PG_Cubw4nv9USBiKKMVaAxS-Ggl6ByqOKusmyK4tp18")
    @GET("/user/profile/check/{profileId}")
    suspend fun signDuplicationCheck(
        @Path("profileId") profileId: String
    ): Response<Any?>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzAsInByb2ZpbGVJZCI6IuyViOuTnO2FjOyKpO2KuOyaqSIsImltYWdlIjpudWxsLCJpYXQiOjE2NzMxMjYzNzgsImV4cCI6MTY3NTcxODM3OCwiaXNzIjoiTXVtZW50In0.PG_Cubw4nv9USBiKKMVaAxS-Ggl6ByqOKusmyK4tp18")
    @Multipart
    @PUT("/user/profile")
    suspend fun putProfile(
        @Part image : MultipartBody.Part?,
        @PartMap body: HashMap<String, RequestBody>
    ) : BaseResponse<SetProfileDto>
}