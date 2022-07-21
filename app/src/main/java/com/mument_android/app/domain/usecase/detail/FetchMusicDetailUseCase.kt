package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.domain.entity.detail.MusicWithMyMumentEntity
import kotlinx.coroutines.flow.Flow

interface FetchMusicDetailUseCase {
    suspend operator fun invoke(musicId: String, userId: String): Flow<MusicWithMyMumentEntity>
}