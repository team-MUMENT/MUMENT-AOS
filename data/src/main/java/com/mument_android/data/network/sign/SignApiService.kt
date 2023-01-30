package com.mument_android.data.network.sign

import com.mument_android.data.util.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SignApiService {
    @GET("/user/profile/check/{profileId}")
    suspend fun signDuplicationCheck(
        @Path("profileId") profileId: String
    ): BaseResponse<Any?>
}