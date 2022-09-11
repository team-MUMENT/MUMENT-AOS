package com.mument_android.app.data.datasource.home

import com.mument_android.app.data.local.recentlist.RecentSearchDAO
import com.mument_android.app.domain.entity.home.RecentSearchData
import javax.inject.Inject

class LocalRecentSearchListDataSourceImpl @Inject constructor(private val dao: RecentSearchDAO) :
    LocalRecentSearchListDataSource {
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