package com.mument_android.data.datasource.home

import com.mument_android.data.local.recentlist.RecentSearchDAO
import com.mument_android.data.local.recentlist.RecentSearchDataEntity
import com.mument_android.domain.entity.home.RecentSearchData
import timber.log.Timber
import javax.inject.Inject

class LocalRecentSearchListDataSourceImpl @Inject constructor(private val dao: RecentSearchDAO) :
    LocalRecentSearchListDataSource {
    override suspend fun getAllRecentSearchList(): Result<List<RecentSearchDataEntity>> =
        runCatching { dao.getAllRecentList() }.onFailure {
            /* TODO ERROR Handing */
        }

    override suspend fun updateRecentSearchList(data: RecentSearchDataEntity) {
        dao.updateRecentSearch(data)
    }

    override suspend fun insertRecentSearchList(data: RecentSearchDataEntity) {
        dao.insertRecentSearch(data)
        val result = dao.getAllRecentList()
        if (result.size > 20) {
            dao.deleteRecentSearch(result[20])   // LRU 기법처럼 가장 뒤에 있는 데이터부터 제거
        }
    }

    override suspend fun deleteRecentSearchList(data: RecentSearchDataEntity) {
        dao.deleteRecentSearch(data)
    }

    override suspend fun deleteAllRecentSearchList() {
        dao.deleteAllRecentSearchList()
    }
}