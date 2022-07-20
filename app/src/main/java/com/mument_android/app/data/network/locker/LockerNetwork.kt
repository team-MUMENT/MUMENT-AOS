package com.mument_android.app.data.network.locker

import com.mument_android.app.data.dto.ResponseMyMumentListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LockerNetwork {
    @GET("/user/my/{userId}/list")
    suspend fun lockerMumentList(
        @Path("userId") userId : String
    ): ResponseMyMumentListDto
}