package com.mument_android.domain.repository.home

import com.mument_android.domain.entity.home.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun updateTodayMument(mument: TodayMument)

    suspend fun insertTodayMument(mument: TodayMument)

    suspend fun deleteTodayMument(mument: TodayMument)

    suspend fun insertRecentSearchList(data: RecentSearchData)

    suspend fun updateRecentSearchList(data: RecentSearchData)

    suspend fun deleteRecentSearchList(data: RecentSearchData)

    suspend fun deleteAllRecentSearchList()

    suspend fun getRecentSearchList(): List<RecentSearchData>?

    suspend fun searchList(keyword: String): List<RecentSearchData>?

    suspend fun getTodayMument(userId: String): Flow<TodayMument>

    suspend fun getBannerMument(): List<BannerEntity>?

    suspend fun getKnownMument(): List<AgainMumentEntity>?

    suspend fun getRandomMument(): RandomMumentEntity?
}