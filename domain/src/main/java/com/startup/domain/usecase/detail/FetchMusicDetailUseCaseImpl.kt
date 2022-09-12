package com.startup.domain.usecase.detail

import com.startup.domain.entity.detail.MusicWithMyMumentEntity
import com.startup.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMusicDetailUseCaseImpl @Inject constructor(
    private val musicDetailRepository: MusicDetailRepository
): FetchMusicDetailUseCase {
    override suspend operator fun invoke(musicId: String, userId: String): Flow<MusicWithMyMumentEntity> =
        musicDetailRepository.fetchMusicDetailInfo(musicId, userId)
}