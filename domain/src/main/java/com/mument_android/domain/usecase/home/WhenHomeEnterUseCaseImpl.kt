package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.AgainMumentEntity
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.home.RandomMumentEntity
import com.mument_android.domain.entity.home.TodayMumentEntity
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WhenHomeEnterUseCaseImpl @Inject constructor(
    val homeRepository: HomeRepository
) : WhenHomeEnterUseCase {
    override suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity> =
        homeRepository.getTodayMument(userId)


    override suspend fun updateLocalTodayMument(mument: TodayMumentEntity) {
        homeRepository.insertTodayMument(mument)
    }

    override suspend fun getBannerMument(): Flow<List<BannerEntity>?> = flow {
        emit(homeRepository.getBannerMument())
    }

    override suspend fun getRandomMument(): Flow<RandomMumentEntity?> = flow {
        emit(homeRepository.getRandomMument())
    }

    override suspend fun getKnownMument(): Flow<List<AgainMumentEntity>?> = flow {
        emit(homeRepository.getKnownMument())
    }

}