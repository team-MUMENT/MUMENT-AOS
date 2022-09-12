package com.startup.domain.repository.detail

import com.startup.domain.entity.detail.MusicWithMyMumentEntity
import kotlinx.coroutines.flow.Flow

interface MusicDetailRepository {
    suspend fun fetchMusicDetailInfo(musicId: String, userId: String): Flow<MusicWithMyMumentEntity>
}