package com.mument_android.data.network.detail

import com.mument_android.data.util.BaseResponse
import com.mument_android.data.dto.MumentListDto
import com.mument_android.data.dto.UserDto
import com.mument_android.data.dto.detail.MumentDetailDto
import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.dto.detail.ResponseBlockUserDto
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

    @GET("/music/{musicId}")
    suspend fun fetchMusicDetailInfo(
        @Path("musicId") musicId: String,
    ): BaseResponse<MusicDetailDto>

    @GET("/music/{musicId}/order")
    suspend fun fetchMumentList(
        @Path("musicId") musicId: String,
        @Query("default") default: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): BaseResponse<MumentListDto>

    @DELETE("/mument/{mumentId}")
    suspend fun deleteMument(
        @Path("mumentId") mumentId: String
    )

    @POST("/user/block/{mumentId}")
    suspend fun blockUser(
        @Path("mumentId") mumentId: String
    ): BaseResponse<ResponseBlockUserDto>

    @GET("/mument/{mumentId}/like")
    suspend fun fetchUsersWhoLikeMument(
        @Path("mumentId") mumentId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): BaseResponse<List<UserDto>>
}