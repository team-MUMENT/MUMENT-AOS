package com.mument_android.app.data.network.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.network.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("/music/search")
    suspend fun searchMusicList(
        @Query("keyword") keyword: String
    ): BaseResponse<List<RecentSearchData>>

    @GET("/{userId}/{musicId}/history?default=Y")
    suspend fun getMumentHistory(
        @Path("userId") userId: String,
        @Path("musicId") musicId: String,
    ): BaseResponse<List<MumentHistoryDto>>

    @GET("/home/recommendation")
    suspend fun getBannerMument(): BaseResponse<List<BannerMumentDto>>

    @GET("/home/today/{userId}")
    suspend fun getTodayMument(
        @Path("userId") userId: String
    ): BaseResponse<TodayMumentDto>

    @GET("/home/known")
    suspend fun getKnownMument(): BaseResponse<List<KnownMumentDto>>

    @GET("/random")
    suspend fun getRandomMument():BaseResponse<List<RandomMumentDto>>
}