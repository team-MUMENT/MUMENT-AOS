package com.mument_android.data.datasource.home

import com.mument_android.data.dto.home.*
import com.mument_android.data.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getBannerMument(): ResultWrapper<BannerMumentDto?>
    suspend fun getTodayMument(): ResultWrapper<TodayMumentDto?>
    suspend fun getKnownMument(): ResultWrapper<KnownMumentDto?>
    suspend fun getRandomMument(): ResultWrapper<RandomMumentDto?>
    suspend fun fetchExistNotifyList(): Exist?
    suspend fun fetchProfileExist(): Boolean?
}