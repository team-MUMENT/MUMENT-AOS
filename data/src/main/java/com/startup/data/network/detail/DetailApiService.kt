package com.startup.data.network.detail

import com.startup.core.base.BaseResponse
import com.startup.data.dto.MumentListDto
import com.startup.data.dto.detail.MumentDetailDto
import com.startup.data.dto.detail.MusicDetailDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApiService {
    @GET("/mument/{mumentId}/{userId}")
    suspend fun fetchMumentDetail(
        @Path ("mumentId") mumentId: String,
        @Path ("userId") userId: String
    ): BaseResponse<MumentDetailDto>

    @GET("/music/{musicId}/{userId}")
    suspend fun fetchMusicDetailInfo(
        @Path("musicId") musicId: String,
        @Path("userId") userId: String
    ): BaseResponse<MusicDetailDto>

    @GET("/music/{musicId}/{userId}/order")
    suspend fun fetchMumentList(
        @Path("musicId") musicId: String,
        @Path("userId") userId: String,
        @Query("default") default: String
    ): BaseResponse<MumentListDto>

    @DELETE("/mument/{mumentId}")
    suspend fun deleteMument(
        @Path("mumentId") mumentId: String
    )
}