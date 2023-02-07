package com.mument_android.data.network.home

import com.mument_android.data.dto.home.NotifyDto
import com.mument_android.data.dto.home.RequestReadNotify
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotifyService {
    @GET("/user/news")
    suspend fun fetchNotifyList(): Response<NotifyDto>

    @PATCH("/user/news/read")
    suspend fun fetchNotifyRead(@Body unreadNews: RequestReadNotify): Response<Void> //val unreadNews:List<Int>

    @PATCH("/user/news/{newsId}")
    suspend fun fetchNotifyDelete(@Path("newsId") newsId: String): Response<Void>
}