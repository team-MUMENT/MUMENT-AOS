package com.mument_android.data.network.app

import com.mument_android.data.dto.LimitUserDto
import com.mument_android.data.util.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface AppApiService {
    @Headers("Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzAsInByb2ZpbGVJZCI6IuyViOuTnO2FjOyKpO2KuOyaqSIsImltYWdlIjpudWxsLCJpYXQiOjE2NzMxMjYzNzgsImV4cCI6MTY3NTcxODM3OCwiaXNzIjoiTXVtZW50In0.PG_Cubw4nv9USBiKKMVaAxS-Ggl6ByqOKusmyK4tp18")
    @GET("/user/report/restrict")
    suspend fun limitUser(): BaseResponse<LimitUserDto?>
}