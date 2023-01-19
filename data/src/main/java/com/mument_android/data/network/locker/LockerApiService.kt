package com.mument_android.data.network.locker

import com.mument_android.data.util.BaseResponse
import com.mument_android.data.dto.locker.LockerMyMumentDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LockerApiService {
    @GET("/user/my/list")
    suspend fun lockerMumentList(
        @Query("tag1") tag1: Int?,
        @Query("tag2") tag2: Int?,
        @Query("tag3") tag3: Int?
    ): BaseResponse<LockerMyMumentDto>

    @GET("/user/like/list")
    suspend fun lockerLikeList(
        @Query("tag1") tag1: Int?,
        @Query("tag2") tag2: Int?,
        @Query("tag3") tag3: Int?
    ): BaseResponse<LockerMyMumentDto>
}