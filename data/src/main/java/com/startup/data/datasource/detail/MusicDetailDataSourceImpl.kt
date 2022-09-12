package com.startup.data.datasource.detail

import com.startup.data.dto.detail.MusicDetailDto
import com.startup.data.network.detail.DetailApiService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class MusicDetailDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MusicDetailDataSource {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
        userId: String
    ): BaseResponse<MusicDetailDto> = detailApiService.fetchMusicDetailInfo(musicId, userId)
}