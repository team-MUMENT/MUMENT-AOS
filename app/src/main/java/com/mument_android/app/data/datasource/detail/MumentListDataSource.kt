package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.MumentListDto
import com.startup.core.base.BaseResponse

interface MumentListDataSource {
    suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): BaseResponse<MumentListDto>
}