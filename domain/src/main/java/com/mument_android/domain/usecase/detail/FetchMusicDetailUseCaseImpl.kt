package com.mument_android.domain.usecase.detail

import com.mument_android.core.network.ApiStatus
import com.mument_android.domain.entity.detail.MusicReqeust
import com.mument_android.domain.entity.detail.MusicWithMyMumentEntity
import com.mument_android.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchMusicDetailUseCaseImpl @Inject constructor(
    private val musicDetailRepository: MusicDetailRepository
): FetchMusicDetailUseCase {
    override suspend operator fun invoke(musicId: String, music: MusicReqeust): Flow<ApiStatus<MusicWithMyMumentEntity>> =
        musicDetailRepository.fetchMusicDetailInfo(musicId, music)
}