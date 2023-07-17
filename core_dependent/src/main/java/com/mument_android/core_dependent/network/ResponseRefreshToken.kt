package com.mument_android.core_dependent.network

import androidx.annotation.Keep

@Keep
data class ResponseRefreshToken(
    val status: Int,
    val message: String,
    val data: RefreshToken?
) {
    data class RefreshToken(
        val id : Int,
        val type: String,
        val accessToken: String,
        val refreshToken: String
    )
}