package com.mument_android.core_dependent.network

import retrofit2.Response

interface TokenDataSource {
    suspend fun refreshAccessToken(token: String): Response<ResponseRefreshToken>
}