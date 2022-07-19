package com.mument_android.app.domain.repository.home

import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTodayMument(): List<TodayMumentEntity>

    suspend fun updateTodayMument(mument: TodayMumentEntity)

    suspend fun insertTodayMument(mument: TodayMumentEntity)

    suspend fun deleteTodayMument(mument: TodayMumentEntity)

    suspend fun getRecentSearchList(): Flow<List<RecentSearchData>>

    suspend fun insertRecentSearchList(data: RecentSearchData)

    suspend fun updateRecentSearchList(data: RecentSearchData)

    suspend fun deleteRecentSearchList(data: RecentSearchData)

    suspend fun deleteAllRecentSearchList()

    suspend fun searchList(keyword: String): Flow<List<RecentSearchData>>

    suspend fun getMumentHistory(userId: String, musicId: String): Flow<MumentHistoryDto>

}