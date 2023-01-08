package com.mument_android.data.repository

import android.util.Log
import com.mument_android.data.datasource.home.*
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.util.ResultWrapper
import com.mument_android.domain.entity.history.MumentHistoryEntity
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
    private val remoteMumentHistoryDataSource: RemoteMumentHistoryDataSource,
    private val remoteSearchListDataSource: RemoteSearchListDataSource,
    private val homeDataSource: HomeDataSource,
    private val randomMumentMapper: RandomMumentMapper
) : HomeRepository {
    // Remote
    override suspend fun searchList(keyword: String): List<RecentSearchData>? =
        remoteSearchListDataSource.searchMusicList(keyword).let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data
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

    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): MumentHistoryEntity? =
        remoteMumentHistoryDataSource.getMumentHistory(userId, musicId).let { response ->
            when (response) {
                is ResultWrapper.Success -> {
                    response.data?.let { history ->
                        MumentHistoryEntity(
                            history.mumentHistory,
                            history.music
                        )
                    }
                }
                is ResultWrapper.GenericError -> {
                    Timber.e("GenericError -> code ${response.code}: message: ${response.message}")
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

    override suspend fun getBannerMument(): List<BannerEntity>? =
        homeDataSource.getBannerMument().let { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.bannerList?.map {
                        BannerEntity(
                            it._id,
                            it.displayDate,
                            Music(it.music._id, it.music.name, it.music.artist, it.music.image),
                            it.tagTitle.replace("\\n", "\n")
                        )
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

    override suspend fun getTodayMument(userId: String): Flow<TodayMumentEntity> = flow {
        localTodayMumentDataSource.getTodayMument(userId).run {
            when (this) { //홈 먼저 검사.
                is ResultWrapper.Success -> {  //제대로 넘어왔을 때 분기처리, null X, 오늘
                    emit(data)
                }
                is ResultWrapper.LocalError -> {
                    Log.e("Locall Error", message.toString())
                    homeDataSource.getTodayMument(userId).map { collectRemote -> // Remote 받아옴
                        when (collectRemote) {
                            is ResultWrapper.Success -> {
                                collectRemote.data?.todayMument?.let { today ->
                                    localTodayMumentDataSource.updateMument(today) // 로컬에 업데이트
                                    emit(today)  //방출
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

