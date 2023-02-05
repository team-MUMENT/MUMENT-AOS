package com.mument_android.data.repository

import android.util.Log
import com.mument_android.data.datasource.home.HomeDataSource
import com.mument_android.data.datasource.home.LocalRecentSearchListDataSource
import com.mument_android.data.datasource.home.LocalTodayMumentDataSource
import com.mument_android.data.datasource.home.RemoteSearchListDataSource
import com.mument_android.data.local.recentlist.RecentSearchDataEntity
import com.mument_android.data.mapper.home.HomeTodayMumentMapper
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.mapper.home.RecentSearchDataMapper
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.home.*
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localTodayMumentDataSource: LocalTodayMumentDataSource,
    private val localRecentSearchListDataSource: LocalRecentSearchListDataSource,
    private val remoteSearchListDataSource: RemoteSearchListDataSource,
    private val homeDataSource: HomeDataSource,
    private val randomMumentMapper: RandomMumentMapper,
    private val todayMumentMapper: HomeTodayMumentMapper,
    private val recentSearchDataMapper: RecentSearchDataMapper
) : HomeRepository {
    // Remote
    override suspend fun searchList(keyword: String): List<RecentSearchData>? =
        remoteSearchListDataSource.searchMusicList(keyword).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.data?.let { recentSearchDataMapper.map(it) }
                }
                is ResultWrapper.GenericError -> {
                    Log.e(
                        "GenericError",
                        "GenericError -> code ${result.code}: message: ${result.message}"
                    )
                    null
                }
                is ResultWrapper.NetworkError -> {
                    Log.e("NetworkError", "$result")
                    null
                }
                else -> {
                    null
                }
            }
        }

    override suspend fun getBannerMument(): List<BannerEntity>? =
        homeDataSource.getBannerMument().let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.data?.bannerList?.map {
                        BannerEntity(
                            it.displayDate,
                            Music(it.music._id, it.music.name, it.music.artist, it.music.image),
                            it.tagTitle.replace("\\n", "\n")
                        )
                    } ?: listOf()
                }
                is ResultWrapper.GenericError -> {
                    Timber.e("GenericError -> code ${result.code}: message: ${result.message}")
                    null
                }
                is ResultWrapper.NetworkError -> {
                    Timber.e("NetworkError")
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
                is ResultWrapper.Success -> {
                    result.data?.data?.againMument
                }
                is ResultWrapper.GenericError -> {
                    Timber.e("GenericError -> code ${result.code}: message: ${result.message}")
                    null
                }
                is ResultWrapper.NetworkError -> {
                    Timber.e("NetworkError")
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
                is ResultWrapper.Success -> {
                    result.data?.let {
                        randomMumentMapper.map(it)
                    }
                }
                is ResultWrapper.GenericError -> {
                    Timber.e("GenericError -> code ${result.code}: message: ${result.message}")
                    null
                }
                is ResultWrapper.NetworkError -> {
                    Timber.e("NetworkError")
                    null
                }
                else -> {
                    null
                }
            }
        }

    override suspend fun fetchExistNotifyList(): Boolean? = homeDataSource.fetchExistNotifyList()

    override suspend fun fetchProfileExist(): Boolean? = homeDataSource.fetchProfileExist()

    override suspend fun getTodayMument(): Flow<TodayMument?> =
        localTodayMumentDataSource.getTodayMument().run {
            when (this) { //홈 먼저 검사.
                is ResultWrapper.Success -> {  //제대로 넘어왔을 때 분기처리, null X, 오늘
                    if (data.todayDate == LocalDate.now().toString()) {
                        todayMumentMapper.map(data)
                    } else {
                        null
                    }
                }
                is ResultWrapper.LocalError -> {
                    Log.e("Local Error", message.toString())
                    null
                }
                else -> {
                    null
                }//네트워크쪽 Result, 따라서 이거는 나중에 Local, Remote로 분리해야 할 듯
            }
        }.let { todayLocal ->
            flow {
                if (todayLocal == null) {
                    homeDataSource.getTodayMument().let { collectRemote -> // Remote 받아옴
                        Log.e("TODAY REMOTE", collectRemote.toString())
                        when (collectRemote) {
                            is ResultWrapper.Success -> {

                                collectRemote.data?.data?.let { today ->
                                    Log.e("TODAY REMOTE TODAY", today.toString())
                                    todayMumentMapper.mapObject(today).let {
                                        localTodayMumentDataSource.insertMument(
                                            todayMumentMapper.mapReverse(
                                                it
                                            )
                                        ) // 로컬에 업데이트
                                        emit(it)  //방출
                                    }
                                }
                            }
                            is ResultWrapper.GenericError -> {
                                Log.e(
                                    "TodayMument",
                                    "GenericError -> code ${collectRemote.code}: message: ${collectRemote.message}"
                                )
                            }
                            is ResultWrapper.NetworkError -> {
                                Log.e("TodayMument", "NetworkError")
                            }
                            else -> {
                            }
                        }
                    }
                } else {
                    Log.e("TODAY Local", todayLocal.toString())
                    emit(todayLocal)
                }
            }
        }

    override suspend fun updateTodayMument(mument: TodayMument) {
        localTodayMumentDataSource.updateMument(todayMumentMapper.mapReverse(mument))
    }

    override suspend fun insertTodayMument(mument: TodayMument) {
        localTodayMumentDataSource.insertMument(todayMumentMapper.mapReverse(mument))
    }

    override suspend fun deleteTodayMument(mument: TodayMument) {
        localTodayMumentDataSource.deleteMument(todayMumentMapper.mapReverse(mument))
    }

    override suspend fun getRecentSearchList(): List<RecentSearchData> {
        localRecentSearchListDataSource.getAllRecentSearchList().getOrThrow().let {
            return recentSearchDataMapper.map(it)
        }
    }

    override suspend fun insertRecentSearchList(data: RecentSearchData) {
        localRecentSearchListDataSource.insertRecentSearchList(
            recentSearchDataMapper.mapReverse(
                data
            )
        )
    }

    override suspend fun updateRecentSearchList(data: RecentSearchData) {
        localRecentSearchListDataSource.updateRecentSearchList(
            RecentSearchDataEntity(
                data._id,
                data.artist,
                data.image,
                data.name,
                Date(System.currentTimeMillis())
            )
        )
    }

    override suspend fun deleteRecentSearchList(data: RecentSearchData) {
        localRecentSearchListDataSource.deleteRecentSearchList(
            recentSearchDataMapper.mapReverse(
                data
            )
        )
    }

    override suspend fun deleteAllRecentSearchList() {
        localRecentSearchListDataSource.deleteAllRecentSearchList()
    }
}

