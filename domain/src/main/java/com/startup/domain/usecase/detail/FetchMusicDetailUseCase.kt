package com.startup.domain.usecase.detail

import com.startup.domain.entity.detail.MusicWithMyMumentEntity
import kotlinx.coroutines.flow.Flow

interface FetchMusicDetailUseCase {
    suspend operator fun invoke(musicId: String, userId: String): Flow<MusicWithMyMumentEntity>
}