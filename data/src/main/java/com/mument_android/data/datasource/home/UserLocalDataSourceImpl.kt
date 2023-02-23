package com.mument_android.data.datasource.home

import com.mument_android.data.local.recentlist.RecentSearchDAO
import com.mument_android.data.local.todaymument.TodayMumentDAO
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val todayDao: TodayMumentDAO,
    private val recentSearchDAO: RecentSearchDAO
) : UserLocalDataSource {
    override suspend fun deleteLocalData(): Int {
        todayDao.dropTodayMument()
        return recentSearchDAO.deleteAllRecentSearchList()
    }
}