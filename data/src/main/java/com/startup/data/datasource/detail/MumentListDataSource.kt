package com.startup.data.datasource.detail

import com.startup.data.dto.MumentListDto
import com.startup.core.base.BaseResponse

interface MumentListDataSource {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): BaseResponse<MumentListDto>
}