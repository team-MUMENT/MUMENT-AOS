package com.mument_android.domain.repository.home

import com.mument_android.domain.entity.history.MumentHistoryEntity
import com.mument_android.domain.entity.home.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun updateTodayMument(mument: TodayMumentEntity)

    suspend fun insertTodayMument(mument: TodayMumentEntity)

    suspend fun deleteTodayMument(mument: TodayMumentEntity)

    suspend fun insertRecentSearchList(data: RecentSearchData)

    suspend fun updateRecentSearchList(data: RecentSearchData)

    suspend fun deleteRecentSearchList(data: RecentSearchData)

    suspend fun deleteAllRecentSearchList()

    suspend fun getRecentSearchList(): List<RecentSearchData>?

    suspend fun searchList(keyword: String): List<RecentSearchData>?

    suspend fun getMumentHistory(userId: String, musicId: String): MumentHistoryEntity?

    suspend fun getTodayMument(userId: String): TodayMumentEntity?

    suspend fun getBannerMument(): List<BannerEntity>?

    suspend fun getKnownMument(): List<AgainMumentEntity>?

    suspend fun getRandomMument(): RandomMumentEntity?
}