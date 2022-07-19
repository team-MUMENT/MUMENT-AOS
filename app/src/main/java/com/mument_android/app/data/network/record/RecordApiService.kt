package com.mument_android.app.data.network.record

import com.mument_android.app.data.dto.record.MumentIsFirstDto
import com.mument_android.app.data.network.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordApiService {
    @GET("/mument/{userId}/{musicId}/is-first")
    suspend fun fetchMumentIsFirst(
        @Path("userId") userId : String,
        @Path("musicId") mumentId : String
    ) : BaseResponse<MumentIsFirstDto>


}