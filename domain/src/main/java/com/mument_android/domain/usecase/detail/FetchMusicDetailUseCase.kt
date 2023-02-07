package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.detail.MusicReqeust
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import kotlinx.coroutines.flow.Flow

interface FetchMusicDetailUseCase {
    suspend operator fun invoke(musicId: String, music: MusicReqeust): Flow<ApiStatus<MusicWithMyMumentEntity>>
}