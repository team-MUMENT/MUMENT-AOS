package com.mument_android.data.datasource.home

import com.mument_android.domain.entity.home.RecentSearchData

interface LocalRecentSearchListDataSource {
    suspend fun getAllRecentSearchList(): Result<List<RecentSearchData>>
    suspend fun updateRecentSearchList(data: RecentSearchData)
    suspend fun insertRecentSearchList(data: RecentSearchData)
    suspend fun deleteRecentSearchList(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}