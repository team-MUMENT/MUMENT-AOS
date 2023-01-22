package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getBannerMument(): ResultWrapper<BannerMumentDto?>
    suspend fun getTodayMument(userId: String): Flow<ResultWrapper<TodayMumentDto?>>
    suspend fun getKnownMument(): ResultWrapper<KnownMumentDto?>
    suspend fun getRandomMument(): ResultWrapper<RandomMumentDto?>
}