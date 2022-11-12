package com.mument_android.data.network.record

import com.mument_android.data.util.BaseResponse
import com.mument_android.data.dto.record.MumentIsFirstDto
import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import retrofit2.http.*

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

    @PUT("/mument/{mumentId}")
    suspend fun putMumentRecord(
        @Path("mumentId")mumentId : String,
        @Body mumentRecordDto: MumentRecordDto
    ) : BaseResponse<ResponseRecordMumentDto>
}