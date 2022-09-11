package com.mument_android.app.domain.usecase.home

import com.mument_android.app.domain.entity.home.AgainMumentEntity
import com.mument_android.app.domain.entity.home.BannerEntity
import com.mument_android.app.domain.entity.home.RandomMumentEntity
import com.mument_android.app.domain.entity.home.TodayMumentEntity
import kotlinx.coroutines.flow.Flow

interface WhenHomeEnterUseCase {
    suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity?>
    suspend fun getBannerMument(): Flow<List<BannerEntity>?>
    suspend fun getRandomMument(): Flow<RandomMumentEntity?>
    suspend fun getKnownMument(): Flow<List<AgainMumentEntity>?>
}