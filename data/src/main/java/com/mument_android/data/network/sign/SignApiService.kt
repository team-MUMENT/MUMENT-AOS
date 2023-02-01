package com.mument_android.data.network.sign

import com.mument_android.data.dto.sign.RequestSetProfileDto
import com.mument_android.data.dto.sign.SetProfileDto
import com.mument_android.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SignApiService {
    @GET("/user/profile/check/{profileId}")
    suspend fun signDuplicationCheck(
        @Path("profileId") profileId: String
    ): Response<Any?>

    @PUT("/user/profile")
    suspend fun putProfile(
        @Body requestSetProfileDto: RequestSetProfileDto
    ) : BaseResponse<SetProfileDto>


}