package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.history.MumentHistoryEntity
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMumentHistoryUseCaseImpl @Inject constructor(val homeRepository: HomeRepository) :
    GetMumentHistoryUseCase {
    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): Flow<MumentHistoryEntity> = flow { homeRepository.getMumentHistory(userId, musicId)?.let { emit(it) } }
}