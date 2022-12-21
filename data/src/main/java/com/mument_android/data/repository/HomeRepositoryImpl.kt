package com.mument_android.data.repository

import com.mument_android.core.network.ApiResult
import com.mument_android.data.datasource.home.*
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.domain.entity.history.MumentHistoryEntity
import com.mument_android.domain.entity.home.*
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.repository.home.HomeRepository
import timber.log.Timber
import java.time.LocalDate
import java.util.*
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
        homeDataSource.getBannerMument().let { result ->
            when (result) {
                is ApiResult.Success -> {
                    result.datas?.bannerList?.map {
                        BannerEntity(
                            it._id,
                            it.displayDate,
                            Music(it.music._id, it.music.name, it.music.artist, it.music.image),
                            it.tagTitle.replace("\\n", "\n")
                        )
                    }
                }
                is ApiResult.Failure -> {
                    null
                }
                else -> {
                    null
                }
            }
        }

    override suspend fun getKnownMument(): List<AgainMumentEntity>? =
        homeDataSource.getKnownMument().let { result ->
            when (result) {
                is ApiResult.Success -> {
                    result.datas?.againMumentEntity
                }
                is ApiResult.Failure -> {
                    null
                }
                else -> {
                    null
                }
            }
        }

    override suspend fun getRandomMument(): RandomMumentEntity? =
        homeDataSource.getRandomMument().let { result ->
            when (result) {
                is ApiResult.Success -> {
                    result.datas?.let {
                        randomMumentMapper.map(it)
                    }
                }
                is ApiResult.Failure -> {
                    null
                }
                else -> {
                    null
                }
            }
        }

    override suspend fun getTodayMument(userId: String): TodayMumentEntity? =
        localTodayMumentDataSource.getTodayMument(userId).getOrNull().let { todayMument ->
            if (todayMument == null || todayMument.todayDate != LocalDate.now().toString()) {
                when (val remoteData = homeDataSource.getTodayMument(userId)) {
                    is ApiResult.Success -> {
                        if (remoteData.datas?.todayMument != null) {
                            localTodayMumentDataSource.updateMument(remoteData.datas!!.todayMument)
                            remoteData.datas!!.todayMument
                        } else {
                            null
                        }
                    }
                    is ApiResult.Failure -> {
                        Timber.e(remoteData.throwable?.message)
                        null
                    }
                    else -> {
                        null
                    }
                }
            } else {
                todayMument
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

