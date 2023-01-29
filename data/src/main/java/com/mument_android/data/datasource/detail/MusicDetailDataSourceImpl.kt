package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.network.detail.DetailApiService
import retrofit2.Response
import javax.inject.Inject

class MusicDetailDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MusicDetailDataSource {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
    ): Response<MusicDetailDto> {
        return detailApiService.fetchMusicDetailInfo(musicId)
    }
}