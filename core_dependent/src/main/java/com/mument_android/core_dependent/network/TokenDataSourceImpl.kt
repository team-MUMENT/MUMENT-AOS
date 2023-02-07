package com.mument_android.core_dependent.network

import retrofit2.Response
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val refreshTokenApiService: RefreshTokenApiService
): TokenDataSource {
    override suspend fun refreshAccessToken(token: String): Response<ResponseRefreshToken> =
        refreshTokenApiService.refreshToken("Bearer $token")
}