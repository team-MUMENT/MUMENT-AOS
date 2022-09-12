package com.startup.domain.usecase.home

import com.startup.domain.entity.home.AgainMumentEntity
import com.startup.domain.entity.home.BannerEntity
import com.startup.domain.entity.home.RandomMumentEntity
import com.startup.domain.entity.home.TodayMumentEntity
import com.startup.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WhenHomeEnterUseCaseImpl @Inject constructor(
    val homeRepository: HomeRepository
) : WhenHomeEnterUseCase {
    override suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity?> =
        homeRepository.getRemoteTodayMument(userId)

    override suspend fun getBannerMument(): Flow<List<BannerEntity>?> =
        homeRepository.getBannerMument()

    override suspend fun getRandomMument(): Flow<RandomMumentEntity?> =
        homeRepository.getRandomMument()

    override suspend fun getKnownMument(): Flow<List<AgainMumentEntity>?> =
        homeRepository.getKnownMument()

}