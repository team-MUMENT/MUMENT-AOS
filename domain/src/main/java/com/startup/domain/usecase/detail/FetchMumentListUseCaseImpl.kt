package com.startup.domain.usecase.detail

import com.startup.domain.entity.detail.MumentSummaryEntity
import com.startup.domain.repository.detail.MumentListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMumentListUseCaseImpl @Inject constructor(
    private val mumentListRepository: MumentListRepository
): FetchMumentListUseCase {
    override suspend fun invoke(
        musicId: String,
        userId: String,
        default: String
    ): Flow<List<MumentSummaryEntity>> =
        mumentListRepository.fetchMumentList(musicId, userId, default)
}