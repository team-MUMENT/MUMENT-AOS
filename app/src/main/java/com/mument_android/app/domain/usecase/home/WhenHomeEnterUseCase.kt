package com.mument_android.app.domain.usecase.home

import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import kotlinx.coroutines.flow.Flow

interface WhenHomeEnterUseCase {
    suspend fun getTodayMument(userId: String): Flow<TodayMumentDto>
    suspend fun getBannerMument(): Flow<List<BannerMumentDto>>
    suspend fun getRandomMument(): Flow<List<RandomMumentDto>>
    suspend fun getKnownMument(): Flow<List<KnownMumentDto>>
}