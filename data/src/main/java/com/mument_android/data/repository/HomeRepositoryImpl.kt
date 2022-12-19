package com.mument_android.data.repository

import android.util.Log
import com.mument_android.core.network.ApiResult
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.datasource.home.*
import com.mument_android.domain.entity.history.MumentHistoryEntity
import com.mument_android.domain.entity.home.*
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localTodayMumentDataSource: LocalTodayMumentDataSource,
    private val localRecentSearchListDataSource: LocalRecentSearchListDataSource,
    private val remoteMumentHistoryDataSource: RemoteMumentHistoryDataSource,
    private val remoteSearchListDataSource: RemoteSearchListDataSource,
    private val homeDataSource: HomeDataSource,
    private val randomMumentMapper: RandomMumentMapper
) : HomeRepository {
    // Remote
    override suspend fun searchList(keyword: String): List<RecentSearchData>? =
        remoteSearchListDataSource.searchMusicList(keyword).data

    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): MumentHistoryEntity? =
        remoteMumentHistoryDataSource.getMumentHistory(userId, musicId).let { response ->
            response.data?.let { history ->
                MumentHistoryEntity(
                    history.mumentHistory,
                    history.music
                )
            }
        }

    override suspend fun getBannerMument(): List<BannerEntity>? =
        homeDataSource.getBannerMument().data?.bannerList


    override suspend fun getKnownMument(): List<AgainMumentEntity>? =
        homeDataSource.getKnownMument().data?.againMumentEntity

    override suspend fun getRandomMument(): RandomMumentEntity? =
        homeDataSource.getRandomMument().data?.let { randomMumentMapper.map(it) }

    override suspend fun getTodayMument(userId: String): TodayMumentEntity? {
        localTodayMumentDataSource.getTodayMument(userId).getOrNull().let { todayMument ->
            if (todayMument == null || todayMument.todayDate != LocalDate.now().toString()) {
                when (val remoteData = homeDataSource.getTodayMument(userId)) {
                    is ApiResult.Success -> {
                        if (remoteData.datas?.todayMument != null) {
                            localTodayMumentDataSource.updateMument(remoteData.datas!!.todayMument)
                            return remoteData.datas!!.todayMument
                        } else {
                            return null
                        }
                    }
                    is ApiResult.Failure -> {
                        Timber.e(remoteData.throwable?.message)
                        return null
                    }
                    else -> {
                        return null
                    }
                }
            } else {
                return todayMument
            }
        }
    }

    override suspend fun updateTodayMument(mument: TodayMumentEntity) {
        localTodayMumentDataSource.updateMument(mument)
    }

    override suspend fun insertTodayMument(mument: TodayMumentEntity) {
        localTodayMumentDataSource.insertMument(mument)
    }

    override suspend fun deleteTodayMument(mument: TodayMumentEntity) {
        localTodayMumentDataSource.deleteMument(mument)
    }

    override suspend fun getRecentSearchList(): List<RecentSearchData> {
        localRecentSearchListDataSource.getAllRecentSearchList().getOrThrow().let {
            return it
        }
    }

    override suspend fun insertRecentSearchList(data: RecentSearchData) {
        localRecentSearchListDataSource.insertRecentSearchList(data)
    }

    override suspend fun updateRecentSearchList(data: RecentSearchData) {
        localRecentSearchListDataSource.updateRecentSearchList(
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
        localRecentSearchListDataSource.deleteRecentSearchList(data)
    }

    override suspend fun deleteAllRecentSearchList() {
        localRecentSearchListDataSource.deleteAllRecentSearchList()
    }

}