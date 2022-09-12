package com.mument_android.app.data.datasource.detail

import com.mument_android.app.data.dto.detail.MusicDetailDto
import com.startup.core.base.BaseResponse
import com.mument_android.app.data.network.detail.DetailApiService
import javax.inject.Inject

class MusicDetailDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MusicDetailDataSource {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
        userId: String
    ): BaseResponse<MusicDetailDto> = detailApiService.fetchMusicDetailInfo(musicId, userId)
}