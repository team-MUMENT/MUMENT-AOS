package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.MumentListDto
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.core.base.BaseResponse
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