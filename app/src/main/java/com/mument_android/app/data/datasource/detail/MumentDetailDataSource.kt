package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.detail.MumentDetailDto
import com.mument_android.app.data.network.base.BaseResponse

interface MumentDetailDataSource {
    suspend fun fetchMumentDetail(mumentId: String, userId: String): BaseResponse<MumentDetailDto>
}