package com.mument_android.app.domain.repository.detail

import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.entity.musicdetail.MusicDetailEntity
import kotlinx.coroutines.flow.Flow

interface MusicDetailRepository {
    suspend fun fetchMusicDetailInfo(musicId: String, userId: String): Flow<MumentCard>
}