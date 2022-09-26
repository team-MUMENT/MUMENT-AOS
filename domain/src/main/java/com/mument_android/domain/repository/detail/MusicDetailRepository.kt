package com.mument_android.domain.repository.detail

import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import kotlinx.coroutines.flow.Flow

interface MusicDetailRepository {
    suspend fun fetchMusicDetailInfo(musicId: String, userId: String): Flow<MusicWithMyMumentEntity>
}