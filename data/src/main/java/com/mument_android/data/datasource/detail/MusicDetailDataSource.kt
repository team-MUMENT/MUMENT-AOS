package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import com.mument_android.domain.entity.detail.MusicReqeust
import kotlinx.coroutines.flow.Flow

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String, musicInfo: MusicReqeust): Flow<MusicDetailDto?>
}