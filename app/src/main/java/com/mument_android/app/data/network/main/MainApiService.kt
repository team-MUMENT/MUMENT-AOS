package com.mument_android.app.data.network.main

import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {
    @POST("/mument/{mumentId}/{userId}/like")
    suspend fun likeMument(
        @Path ("mumentId") mumentId: Int,
        @Path ("userId") userId: Int
    )
}