package com.mument_android.data.network.locker

import com.mument_android.core.base.BaseResponse
import com.mument_android.data.dto.locker.LockerMyMumentDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LockerApiService {
    @GET("/user/my/{userId}/list")
    suspend fun lockerMumentList(
        @Path("userId") userId : String,
        @Query("tag1") tag1: Int?,
        @Query("tag2") tag2: Int?,
        @Query("tag3") tag3: Int?
    ): BaseResponse<LockerMyMumentDto>

    @GET("/user/like/{userId}/list")
    suspend fun lockerLikeList(
        @Path("userId") userId : String,
        @Query("tag1") tag1: Int?,
        @Query("tag2") tag2: Int?,
        @Query("tag3") tag3: Int?
    ): BaseResponse<LockerMyMumentDto>
}