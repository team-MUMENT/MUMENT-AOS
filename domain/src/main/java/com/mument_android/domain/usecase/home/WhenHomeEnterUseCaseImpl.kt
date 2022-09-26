package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.AgainMumentEntity
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.home.RandomMumentEntity
import com.mument_android.domain.entity.home.TodayMumentEntity
import com.mument_android.domain.repository.home.HomeRepository
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