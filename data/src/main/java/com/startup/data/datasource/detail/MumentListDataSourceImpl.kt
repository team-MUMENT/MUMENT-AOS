package com.startup.data.datasource.detail

import com.startup.data.dto.MumentListDto
import com.startup.data.network.detail.DetailApiService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class MumentListDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MumentListDataSource {
    override suspend fun fetchMumentList(
        musicId: String,
        userId: String,
        default: String
    ): BaseResponse<MumentListDto> = detailApiService.fetchMumentList(musicId, userId, default)
}