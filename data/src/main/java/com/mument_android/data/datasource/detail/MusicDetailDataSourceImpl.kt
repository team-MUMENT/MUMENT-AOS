package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.data.network.detail.DetailApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicDetailDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MusicDetailDataSource {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
    ): Flow<MusicDetailDto?> = flow {
        emit(detailApiService.fetchMusicDetailInfo(musicId).data)
    }
}