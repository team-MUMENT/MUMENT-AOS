package com.startup.data.network.home

import com.startup.domain.entity.home.RecentSearchData
import com.startup.core.base.BaseResponse
import com.startup.data.dto.history.MumentHistoryDto
import com.startup.data.dto.home.BannerMumentDto
import com.startup.data.dto.home.KnownMumentDto
import com.startup.data.dto.home.RandomMumentDto
import com.startup.data.dto.home.TodayMumentDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("/music/search")
    suspend fun searchMusicList(
        @Query("keyword") keyword: String
    ): BaseResponse<List<RecentSearchData>>

    @GET("mument/{userId}/{musicId}/history?default=Y")
    suspend fun getMumentHistory(
        @Path("userId") userId: String,
        @Path("musicId") musicId: String,
    ): BaseResponse<MumentHistoryDto>

    @GET("/mument/banner")
    suspend fun getBannerMument(): BaseResponse<BannerMumentDto>

    @GET("/mument/today")
    suspend fun getTodayMument(
        @Query("userId") userId: String
    ): BaseResponse<TodayMumentDto>?

    @GET("/mument/again")
    suspend fun getKnownMument(): BaseResponse<KnownMumentDto>

    @GET("/mument/random")
    suspend fun getRandomMument(): BaseResponse<RandomMumentDto>
}