package com.mument_android.app.domain.repository.home

import com.mument_android.app.domain.entity.history.MumentHistoryEntity
import com.mument_android.app.domain.entity.home.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getLocalTodayMument(userId: String): Flow<TodayMumentEntity>

    suspend fun updateTodayMument(mument: TodayMumentEntity)

    suspend fun insertTodayMument(mument: TodayMumentEntity)

    suspend fun deleteTodayMument(mument: TodayMumentEntity)

    suspend fun getRecentSearchList(): Flow<List<RecentSearchData>>

    suspend fun insertRecentSearchList(data: RecentSearchData)

    suspend fun updateRecentSearchList(data: RecentSearchData)

    suspend fun deleteRecentSearchList(data: RecentSearchData)

    suspend fun deleteAllRecentSearchList()

    suspend fun searchList(keyword: String): Flow<List<RecentSearchData>>

    suspend fun getMumentHistory(userId: String, musicId: String): Flow<MumentHistoryEntity>

    suspend fun getRemoteTodayMument(userId: String): Flow<TodayMumentEntity>

    suspend fun getBannerMument(): Flow<List<BannerEntity>>

    suspend fun getKnownMument(): Flow<List<AgainMumentEntity>>

    suspend fun getRandomMument(): Flow<RandomMumentEntity>
}