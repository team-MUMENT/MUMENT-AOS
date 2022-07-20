package com.mument_android.app.data.network.record

import com.mument_android.app.data.dto.record.MumentIsFirstDto
import com.mument_android.app.data.dto.record.MumentRecordDto
import com.mument_android.app.data.dto.record.ResponseRecordMumentDto
import com.mument_android.app.data.network.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordApiService {
    @GET("/mument/{userId}/{musicId}/is-first")
    suspend fun fetchMumentIsFirst(
        @Path("userId") userId : String,
        @Path("musicId") musicId : String
    ) : BaseResponse<MumentIsFirstDto>

    @POST("/mument/{userId}/{musicId}")
    suspend fun postMumentRecord(
        @Path("musicId") musicId : String,
        @Path("userId") userId : String,
        @Body mumentRecordDto: MumentRecordDto
    ) : BaseResponse<ResponseRecordMumentDto>
}