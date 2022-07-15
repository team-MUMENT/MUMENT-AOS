package com.mument_android.app.data.network.locker

import com.mument_android.app.data.dto.ResponseMyMumentListDto
import retrofit2.http.GET

interface LockerNetwork {
    @GET("")
    suspend fun fetchLockerMumentList(): ResponseMyMumentListDto
}