package com.mument_android.core_dependent.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RefreshTokenApiService {

    @GET("/auth/token")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ): Response<ResponseRefreshToken>
}