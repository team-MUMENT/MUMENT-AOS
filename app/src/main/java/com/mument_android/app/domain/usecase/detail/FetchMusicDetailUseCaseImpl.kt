package com.mument_android.app.domain.usecase.detail

import com.mument_android.app.domain.entity.MumentCard
import com.mument_android.app.domain.repository.detail.MusicDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMusicDetailUseCaseImpl @Inject constructor(
    private val musicDetailRepository: MusicDetailRepository
): FetchMusicDetailUseCase {
    override suspend operator fun invoke(musicId: String, userId: String): Flow<MumentCard> =
        musicDetailRepository.fetchMusicDetailInfo(musicId, userId)
}