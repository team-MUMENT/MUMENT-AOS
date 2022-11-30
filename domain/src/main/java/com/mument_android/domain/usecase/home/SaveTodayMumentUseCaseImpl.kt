package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.TodayMumentEntity
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveTodayMumentUseCaseImpl @Inject constructor(val homeRepository: HomeRepository) :
    SaveTodayMumentUseCase {
    override suspend fun saveTodayMument(mument: TodayMumentEntity) {
        homeRepository.insertTodayMument(mument)
    }

    override suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity> = flow {
        homeRepository.getLocalTodayMument(userId)
            ?.let { emit(it) }
    }
}