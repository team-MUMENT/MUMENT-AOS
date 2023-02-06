package com.mument_android.domain.usecase.home

import com.mument_android.domain.entity.home.AgainMumentEntity
import com.mument_android.domain.entity.home.BannerEntity
import com.mument_android.domain.entity.home.RandomMumentEntity
import com.mument_android.domain.entity.home.TodayMument
import kotlinx.coroutines.flow.Flow

interface WhenHomeEnterUseCase {
    suspend fun getTodayMument(): Flow<TodayMument?>
    suspend fun updateLocalTodayMument(mument: TodayMument)
    suspend fun getBannerMument(): Flow<List<BannerEntity>?>
    suspend fun getRandomMument(): Flow<RandomMumentEntity?>
    suspend fun getKnownMument(): Flow<List<AgainMumentEntity>?>
}