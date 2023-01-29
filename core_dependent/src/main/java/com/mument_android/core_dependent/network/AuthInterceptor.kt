package com.mument_android.core_dependent.network

import android.util.Log
import com.mument_android.core_dependent.BuildConfig
import com.mument_android.core_dependent.ext.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStoreManager.writeAccessToken(BuildConfig.TEST_ACCESS_TOKEN)
            dataStoreManager.accessTokenFlow.first()
        }
        val request = chain.request()
            .newBuilder()
            .addHeaders(accessToken)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private fun Request.Builder.addHeaders(token: String?) = this.apply { header("Authorization", BEARER + token.toString()) }
        private const val BEARER = "Bearer "
    }
}