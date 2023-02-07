package com.mument_android.core_dependent.network

import android.content.Context
import android.widget.Toast
import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.core_dependent.ext.collectFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
): Interceptor {
    private var countOfExpiresMessage = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {

            collectFlow(dataStoreManager.accessTokenFlow) {
                dataStoreManager.writeAccessToken(it?:"")
                if(it==null){
                    dataStoreManager.writeRefreshToken(it?:"")
                    dataStoreManager.refreshTokenFlow.first()
                }
            }
            //dataStoreManager.writeAccessToken(DataStoreManager.ACCESS_TOKEN_KEY.toString())
            dataStoreManager.accessTokenFlow.first()
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

    private fun refreshAccessToken() {
        runBlocking {
            dataStoreManager.refreshTokenFlow.first()?.let {
                val apiCall = tokenDataSource.refreshAccessToken(it)
                if(apiCall.isSuccessful) {
                    apiCall.body()?.data?.let {
                        dataStoreManager.writeAccessToken(it.accessToken)
                        dataStoreManager.writeRefreshToken(it.refreshToken)
                    }
                }
            }
        }
    }

    private fun retryConnection(chain: Interceptor.Chain, context: Context, previousResponse: Response): Response {
        var response = previousResponse
        var retryCount = 0
        val responseCodes = mutableListOf(response.code)
        do {
            response.close()
            refreshAccessToken()
            val token = runBlocking { dataStoreManager.refreshTokenFlow.first() }
            val newRequest = chain.request().newBuilder()
                .addHeaders(token)
                .build()
            response = chain.proceed(newRequest)
            responseCodes.add(response.code)
            retryCount++
        } while (response.code == HttpURLConnection.HTTP_UNAUTHORIZED && retryCount < MAX_RETRY_COUNT)

        if(response.code == HttpURLConnection.HTTP_UNAUTHORIZED && retryCount == MAX_RETRY_COUNT) {
            countOfExpiresMessage++
            if (countOfExpiresMessage == 1) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "세션이 만료되었습니다. 다시 로그인해주세요:)", Toast.LENGTH_SHORT).show()
                }
            }
            runBlocking {
                dataStoreManager.writeAccessToken("")
                dataStoreManager.writeRefreshToken("")
            }
        }
        return response
    }

    companion object {
        private const val MAX_RETRY_COUNT= 2
        private fun Request.Builder.addHeaders(token: String?) = this.apply { header("Authorization", BEARER + token.toString()) }
        private const val BEARER = "Bearer "
    }
}