package com.mument_android.data.network

import android.content.Context
import android.widget.Toast
import com.angdroid.navigation.LogInNavigatorProvider
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.network.TokenDataSource
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context,
    private val dataStoreManager: DataStoreManager,
    private val tokenDataSource: TokenDataSource
) : Interceptor {
    private val entryPoint by lazy { EntryPoints.get(context, LoginModuleDependencies::class.java) }
    private val loginNavigatorProvider = entryPoint.provideLoginNavigatorProvider()
    private var countOfUnAuth = 0


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface LoginModuleDependencies {
        fun provideLoginNavigatorProvider(): LogInNavigatorProvider
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStoreManager.accessTokenFlow.first() ?: ""
        }
        val request = chain.request()
            .newBuilder()
            .addHeaders(accessToken)
            .build()
        val response = chain.proceed(request)
        return if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            retryConnection(chain, context, response)
        } else {
            response
        }
    }

    private fun retryConnection(
        chain: Interceptor.Chain,
        context: Context,
        previousResponse: Response
    ): Response {
        var response = previousResponse
        var retryCount = 0
        do {
            response.close()
            refreshAccessToken()
            val token = runBlocking { dataStoreManager.accessTokenFlow.first() }
            val newRequest = chain.request().newBuilder()
                .addHeaders(token)
                .build()
            response = chain.proceed(newRequest)
            retryCount++
        } while (response.code == HttpURLConnection.HTTP_UNAUTHORIZED && retryCount < MAX_RETRY_COUNT)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED && retryCount == MAX_RETRY_COUNT) {
            countOfUnAuth++
            if (countOfUnAuth == 1) {
                runBlocking(Dispatchers.Main) {
                    Toast.makeText(context, "세션이 만료되었습니다. 다시 로그인해주세요:)", Toast.LENGTH_SHORT).show()
                }
                loginNavigatorProvider.navToLogin()
            }
            runBlocking {
                dataStoreManager.writeAccessToken("")
                dataStoreManager.writeRefreshToken("")
            }
        }
        return response
    }

    private fun refreshAccessToken() {
        runBlocking {
            dataStoreManager.refreshTokenFlow.first()?.let {
                val apiCall = tokenDataSource.refreshAccessToken(it)
                if (apiCall.isSuccessful) {
                    apiCall.body()?.data?.let { tokens ->
                        dataStoreManager.writeAccessToken(tokens.accessToken)
                        dataStoreManager.writeRefreshToken(tokens.refreshToken)
                    }
                }
            }
        }
    }

    companion object {
        private const val MAX_RETRY_COUNT = 2
        private fun Request.Builder.addHeaders(token: String?) =
            this.apply { header("Authorization", BEARER + token.toString()) }
        private const val BEARER = "Bearer "
    }
}