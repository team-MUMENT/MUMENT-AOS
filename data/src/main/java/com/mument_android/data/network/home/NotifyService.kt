package com.mument_android.data.network.home

import com.mument_android.data.dto.home.NotifyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface NotifyService {
    @GET("/user/news")
    suspend fun fetchNotifyList(): Response<NotifyDto>

    @PATCH("/user/news/read")
    suspend fun fetchNotifyRead(@Query("unreadNews") unreadNews: List<Int>): Response<Void>

    @PATCH("/user/news/{newsId}")
    suspend fun fetchNotifyDelete(@Path("newsId") newsId: String): Response<Void>
}