package com.mument_android.app.data.network.detail

import com.mument_android.app.data.dto.ResponseMumentDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApiService {
    @GET("/mument/{mumentId}/{userId}")
    suspend fun fetchMumentDetail(
        @Path ("mumentId") mumentId: Int,
        @Path ("userId") userId: Int
    ): ResponseMumentDetailDto
}