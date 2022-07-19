package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.home.LocalRecentSearchListDataSource
import com.mument_android.app.data.datasource.home.LocalTodayMumentDataSource
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val todayMumentDataSource: LocalTodayMumentDataSource,
    private val recentSaerchListDataSource: LocalRecentSearchListDataSource
) : HomeRepository {
    override suspend fun getTodayMument(): List<TodayMumentEntity> =
        todayMumentDataSource.getTodayMument()


    override suspend fun updateTodayMument(mument: TodayMumentEntity) {
        todayMumentDataSource.updateMument(mument)
    }

    override suspend fun insertTodayMument(mument: TodayMumentEntity) {
        todayMumentDataSource.insertMument(mument)
    }

    override suspend fun deleteTodayMument(mument: TodayMumentEntity) {
        todayMumentDataSource.deleteMument(mument)
    }

    override suspend fun getRecentSearchList(): Flow<List<RecentSearchData>> = flow {
        emit(recentSaerchListDataSource.getAllRecentSearchList())
    }.flowOn(Dispatchers.IO)


    override suspend fun insertRecentSearchList(data: RecentSearchData) {
        recentSaerchListDataSource.insertRecentSearchList(data)
    }

    override suspend fun updateRecentSearchList(data: RecentSearchData) {
        recentSaerchListDataSource.updateRecentSearchList(
            RecentSearchData(
                data._id,
                data.artist,
                data.image,
                data.name,
                Date(System.currentTimeMillis())
            )
        )
    }

    override suspend fun deleteRecentSearchList(data: RecentSearchData) {
        recentSaerchListDataSource.deleteRecentSearchList(data)
    }

    override suspend fun deleteAllRecentSearchList() {
        recentSaerchListDataSource.deleteAllRecentSearchList()
    }
}