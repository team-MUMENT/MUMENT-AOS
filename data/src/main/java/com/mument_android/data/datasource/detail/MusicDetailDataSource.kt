package com.mument_android.data.datasource.detail

import com.mument_android.data.dto.detail.MusicDetailDto
import kotlinx.coroutines.flow.Flow

interface MusicDetailDataSource {
    suspend fun fetchMusicDetailInfo(musicId: String): Flow<MusicDetailDto?>
}