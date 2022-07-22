package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveTodayMumentUseCaseImpl @Inject constructor(val homeRepository: HomeRepository):SaveTodayMumentUseCase {
    override suspend fun saveTodayMument(mument: TodayMumentEntity) {
        homeRepository.insertTodayMument(mument)
    }

    override suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity> = homeRepository.getLocalTodayMument(userId)
}