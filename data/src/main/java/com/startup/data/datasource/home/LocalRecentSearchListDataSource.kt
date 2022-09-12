package com.startup.data.datasource.home

import com.startup.domain.entity.home.RecentSearchData

interface LocalRecentSearchListDataSource {

    suspend fun getAllRecentSearchList(): List<RecentSearchData>
    suspend fun updateRecentSearchList(data: RecentSearchData)
    suspend fun insertRecentSearchList(data: RecentSearchData)
    suspend fun deleteRecentSearchList(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}