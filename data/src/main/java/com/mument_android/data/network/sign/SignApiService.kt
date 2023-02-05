package com.mument_android.data.network.sign

import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.util.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SignApiService {
    @GET("/user/profile/check/{profileId}")
    suspend fun signDuplicationCheck(
        @Path("profileId") profileId: String
    ): Response<Any?>

    @Multipart
    @PUT("/user/profile")
    suspend fun putProfile(
        @Part image : MultipartBody.Part?,
        @PartMap body: HashMap<String, RequestBody>
    ) : BaseResponse<SetProfileDto>
}