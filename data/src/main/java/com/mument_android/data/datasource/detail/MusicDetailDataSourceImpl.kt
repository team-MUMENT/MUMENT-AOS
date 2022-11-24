package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.util.BaseResponse
import javax.inject.Inject

class MusicDetailDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MusicDetailDataSource {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
        userId: String
    ): BaseResponse<MusicDetailDto> = detailApiService.fetchMusicDetailInfo(musicId, userId)
}