package com.mument_android.app.data.datasource.home

import com.mument_android.app.domain.entity.home.recentlist.RecentSearchData

interface LocalRecentSearchListDataSource {

    suspend fun getAllRecentSearchList(): List<RecentSearchData>
    suspend fun updateRecentSearchList(data: RecentSearchData)
    suspend fun insertRecentSearchList(data: RecentSearchData)
    suspend fun deleteRecentSearchList(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}