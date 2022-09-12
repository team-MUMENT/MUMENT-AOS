package com.startup.data.network.main

import com.startup.core.base.BaseResponse
import com.startup.data.dto.LikeCountDto
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {
    @POST("/mument/{mumentId}/{userId}/like")
    suspend fun likeMument(
        @Path ("mumentId") mumentId: String,
        @Path ("userId") userId: String
    ): BaseResponse<LikeCountDto>

    @DELETE("/mument/{mumentId}/{userId}/like")
    suspend fun cancelLikeMument(
        @Path("mumentId") mumentId: String,
        @Path("userId") userId: String
    ): BaseResponse<LikeCountDto>
}