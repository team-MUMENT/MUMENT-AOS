package com.mument_android.data.network.app

import com.mument_android.data.dto.LimitUserDto
import com.mument_android.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface AppApiService {
    @GET("/user/report/restrict")
    suspend fun limitUser(): BaseResponse<LimitUserDto?>
}