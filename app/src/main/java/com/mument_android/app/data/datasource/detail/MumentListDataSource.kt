package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.MumentListDto
import com.mument_android.app.data.network.base.BaseResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface MumentListDataSource {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): BaseResponse<MumentListDto>
}