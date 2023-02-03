package com.mument_android.data.repository

import android.util.Log
import com.mument_android.data.datasource.home.*
import com.mument_android.data.local.recentlist.RecentSearchDataEntity
import com.mument_android.data.mapper.home.HomeTodayMumentMapper
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.mapper.home.RecentSearchDataMapper
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.history.MumentHistory
import com.mument_android.domain.entity.home.*
import com.mument_android.domain.entity.musicdetail.musicdetaildata.Music
import com.mument_android.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
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
                    result.data?.list?.let { recentSearchDataMapper.map(it) }
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
                    Log.e("Result", result.toString())
                    result.data?.data?.bannerList?.map {
                        BannerEntity(
                            it._id,
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
                    result.data?.againMumentEntity
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
                is ResultWrapper.Success -> { //현재 데이터 타입 불일치 따라서 data class로 변환이 안 됨.
                    Log.e("Success Collect!!!", result.data.toString())
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

    override suspend fun getTodayMument(userId: String): Flow<TodayMument> = flow {
        localTodayMumentDataSource.getTodayMument(userId).run {
            when (this) { //홈 먼저 검사.
                is ResultWrapper.Success -> {  //제대로 넘어왔을 때 분기처리, null X, 오늘
                    emit(todayMumentMapper.map(data))
                }
                is ResultWrapper.LocalError -> {
                    Log.e("Locall Error", message.toString())
                    homeDataSource.getTodayMument(userId).map { collectRemote -> // Remote 받아옴
                        when (collectRemote) {
                            is ResultWrapper.Success -> {
                                collectRemote.data?.todayMument?.let { today ->
                                    localTodayMumentDataSource.updateMument(today) // 로컬에 업데이트
                                    emit(todayMumentMapper.map(today))  //방출
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
                            else -> {}
                        }
                    }
                }
                else -> {}//네트워크쪽 Result, 따라서 이거는 나중에 Local, Remote로 분리해야 할 듯
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

