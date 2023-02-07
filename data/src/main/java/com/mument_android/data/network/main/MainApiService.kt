package com.mument_android.data.network.main

import com.mument_android.data.util.BaseResponse
import com.mument_android.data.dto.LikeCountDto
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {
    @POST("/mument/{mumentId}/like")
    suspend fun likeMument(
        @Path ("mumentId") mumentId: String
    ): BaseResponse<LikeCountDto>

    @DELETE("/mument/{mumentId}/like")
    suspend fun cancelLikeMument(
        @Path("mumentId") mumentId: String
    ): BaseResponse<LikeCountDto>
}