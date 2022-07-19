package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.recentlist.RecentSearchDAO
import com.mument_android.app.data.local.recentlist.RecentSearchData
import javax.inject.Inject

class RecentSearchListDataSourceImpl @Inject constructor(private val dao: RecentSearchDAO) :
    RecentSearchListDataSource {
    override suspend fun getAllRecentSearchList(): List<RecentSearchData> = dao.getAllRecentList()


    override suspend fun updateRecentSearchList(data: RecentSearchData) {
        dao.updateRecentSearch(data)
    }

    override suspend fun insertRecentSearchList(data: RecentSearchData) {
        dao.insertRecentSearch(data)
    }

    override suspend fun deleteRecentSearchList(data: RecentSearchData) {
        dao.deleteRecentSearch(data)
    }

    override suspend fun deleteAllRecentSearchList() {
        dao.deleteAllRecentSearchList()
    }
}