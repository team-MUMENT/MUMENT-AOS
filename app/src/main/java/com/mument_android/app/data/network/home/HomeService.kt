package com.mument_android.app.data.network.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.base.BaseResponse
import retrofit2.http.Body
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
    suspend fun getRandomMument():BaseResponse<RandomMumentDto>
}