package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.MumentListDto
import com.mument_android.core.base.BaseResponse

interface MumentListDataSource {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): BaseResponse<MumentListDto>
}