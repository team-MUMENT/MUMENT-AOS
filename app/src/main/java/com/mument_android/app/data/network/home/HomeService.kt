package com.mument_android.app.data.network.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
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
    ): BaseResponse<MumentHistoryDto>
}