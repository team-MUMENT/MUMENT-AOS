package com.mument_android.data.network.home

import com.mument_android.data.dto.history.MumentHistoryDto
import com.mument_android.data.dto.home.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("/music/search")
    suspend fun searchMusicList(
        @Query("keyword") keyword: String
    ): Response<RecentSearchDataDto>

    @GET("mument/{userId}/{musicId}/history?default=Y")
    suspend fun getMumentHistory(
        @Path("userId") userId: String,
        @Path("musicId") musicId: String,
    ): Response<MumentHistoryDto>

    @GET("/mument/banner")
    suspend fun getBannerMument(): Response<BannerMumentDto>

    @GET("/mument/today")
    suspend fun getTodayMument(
        @Query("userId") userId: String
    ): Response<TodayMumentDto>

    @GET("/mument/again")
    suspend fun getKnownMument(): Response<KnownMumentDto>

    @GET("/mument/random")
    suspend fun getRandomMument(): Response<RandomMumentDto>
}