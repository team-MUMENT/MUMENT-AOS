package com.mument_android.data.datasource.home

import com.mument_android.data.local.recentlist.RecentSearchDataEntity

interface LocalRecentSearchListDataSource {
    suspend fun getAllRecentSearchList(): Result<List<RecentSearchDataEntity>>
    suspend fun updateRecentSearchList(data: RecentSearchDataEntity)
    suspend fun insertRecentSearchList(data: RecentSearchDataEntity)
    suspend fun deleteRecentSearchList(data: RecentSearchDataEntity)
    suspend fun deleteAllRecentSearchList()
}