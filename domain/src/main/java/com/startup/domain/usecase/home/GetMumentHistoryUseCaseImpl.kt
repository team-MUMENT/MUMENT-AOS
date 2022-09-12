package com.startup.domain.usecase.home

import com.startup.domain.entity.history.MumentHistoryEntity
import com.startup.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMumentHistoryUseCaseImpl @Inject constructor(val homeRepository: HomeRepository) :
    GetMumentHistoryUseCase {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): Flow<MumentHistoryEntity> = homeRepository.getMumentHistory(userId, musicId)

}