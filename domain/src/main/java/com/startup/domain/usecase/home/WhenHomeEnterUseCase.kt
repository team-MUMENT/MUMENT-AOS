package com.startup.domain.usecase.home

import com.startup.domain.entity.home.AgainMumentEntity
import com.startup.domain.entity.home.BannerEntity
import com.startup.domain.entity.home.RandomMumentEntity
import com.startup.domain.entity.home.TodayMumentEntity
import kotlinx.coroutines.flow.Flow

interface WhenHomeEnterUseCase {
    suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity?>
    suspend fun getBannerMument(): Flow<List<BannerEntity>?>
    suspend fun getRandomMument(): Flow<RandomMumentEntity?>
    suspend fun getKnownMument(): Flow<List<AgainMumentEntity>?>
}