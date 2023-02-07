package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.BannerMumentDto
import com.mument_android.data.dto.home.KnownMumentDto
import com.mument_android.data.dto.home.RandomMumentDto
import com.mument_android.data.dto.home.TodayMumentDto
import com.mument_android.data.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getBannerMument(): ResultWrapper<BannerMumentDto?>
    suspend fun getTodayMument(): ResultWrapper<TodayMumentDto?>
    suspend fun getKnownMument(): ResultWrapper<KnownMumentDto?>
    suspend fun getRandomMument(): ResultWrapper<RandomMumentDto?>
    suspend fun fetchExistNotifyList(): Boolean?
    suspend fun fetchProfileExist(): Boolean?
}