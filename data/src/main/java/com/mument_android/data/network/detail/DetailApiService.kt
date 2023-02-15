package com.mument_android.data.network.detail

import com.mument_android.data.dto.LikedUserDto
import com.mument_android.data.util.BaseResponse
import com.mument_android.data.dto.MumentListDto
import com.mument_android.data.dto.TempUserDto
import com.mument_android.data.dto.detail.*
import com.mument_android.domain.entity.detail.MusicReqeust
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailApiService {
    @GET("/mument/{mumentId}")
    suspend fun fetchMumentDetail(
        @Path ("mumentId") mumentId: String
    ): BaseResponse<MumentDetailDto>

    @POST("/music/{musicId}")
    suspend fun fetchMusicDetailInfo(
        @Path("musicId") musicId: String,
        @Body musicInfo: MusicReqeust
    ): BaseResponse<MyMusicDetailDto>

    @GET("/music/{musicId}/order")
    suspend fun fetchMumentList(
        @Path("musicId") musicId: String,
        @Query("default") default: String,
    ): BaseResponse<MumentListDto>

    @DELETE("/mument/{mumentId}")
    suspend fun deleteMument(
        @Path("mumentId") mumentId: String
    )

    @POST("/user/block/{mumentId}")
    suspend fun blockUser(
        @Path("mumentId") mumentId: String
    ): ResponseBlockUserDto

    @GET("/mument/{mumentId}/like")
    suspend fun fetchUsersWhoLikeMument(
        @Path("mumentId") mumentId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<LikedUserDto>

    @POST("/mument/report/{mumentId}")
    suspend fun reportMument(
        @Path("mumentId") mumentId: String,
        @Body requestReportDto: RequestReportDto
    ) : BaseResponse<Void>
}