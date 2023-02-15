package com.mument_android.data.network.detail

import com.mument_android.data.dto.history.MumentHistoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HistoryService {
    @GET("mument/{musicId}/{userId}/history?")
    suspend fun getMumentHistory(
        @Path("userId") userId: String,
        @Path("musicId") musicId: String,
        @Query("default") default: String,
    ): Response<MumentHistoryDto>
}