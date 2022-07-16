package com.mument_android.app.data.network.detail

import com.mument_android.app.data.dto.detail.MumentDetailDto
import com.mument_android.app.data.network.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApiService {
    @GET("/mument/{mumentId}/{userId}")
    suspend fun fetchMumentDetail(
        @Path ("mumentId") mumentId: String,
        @Path ("userId") userId: String
    ): BaseResponse<MumentDetailDto>
}