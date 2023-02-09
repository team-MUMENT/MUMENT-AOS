package com.mument_android.data.network.record

import com.mument_android.data.util.BaseResponse
import com.mument_android.data.dto.record.MumentIsFirstDto
import com.mument_android.data.dto.record.MumentModifyDto
import com.mument_android.data.dto.record.MumentRecordDto
import com.mument_android.data.dto.record.ResponseRecordMumentDto
import retrofit2.http.*

interface RecordApiService {
    @GET("/mument/{musicId}/is-first")
    suspend fun fetchMumentIsFirst(
        @Path("musicId") musicId: String
    ): BaseResponse<MumentIsFirstDto>

    @POST("/mument/{musicId}")
    suspend fun postMumentRecord(
        @Path("musicId") musicId: String,
        @Body mumentRecordDto: MumentRecordDto
    ): BaseResponse<ResponseRecordMumentDto>

    @PUT("/mument/{mumentId}")
    suspend fun putMumentRecord(
        @Path("mumentId") mumentId: String,
        @Body mumentModifyDto: MumentModifyDto
    ): BaseResponse<ResponseRecordMumentDto>
}