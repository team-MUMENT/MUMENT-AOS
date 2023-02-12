package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MyMusicDetailDto
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.domain.entity.detail.MusicReqeust
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicDetailDataSourceImpl @Inject constructor(
    private val detailApiService: DetailApiService
): MusicDetailDataSource {
    override suspend fun fetchMusicDetailInfo(
        musicId: String,
        musicInfo: MusicReqeust
    ): Flow<MyMusicDetailDto?> = flow {
        emit(detailApiService.fetchMusicDetailInfo(musicId, musicInfo).data)
    }
}