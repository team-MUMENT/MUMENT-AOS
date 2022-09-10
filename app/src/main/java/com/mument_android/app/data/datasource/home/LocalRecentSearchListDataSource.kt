package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.recentlist.RecentSearchData

interface LocalRecentSearchListDataSource {

    suspend fun getAllRecentSearchList(): List<RecentSearchData>
    suspend fun updateRecentSearchList(data: RecentSearchData)
    suspend fun insertRecentSearchList(data: RecentSearchData)
    suspend fun deleteRecentSearchList(data: RecentSearchData)
    suspend fun deleteAllRecentSearchList()
}