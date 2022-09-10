package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.home.*
import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import com.mument_android.app.domain.entity.history.MumentHistoryEntity
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localTodayMumentDataSource: LocalTodayMumentDataSource,
    private val localRecentSaerchListDataSource: LocalRecentSearchListDataSource,
    private val remoteMumentHistoryDataSource: RemoteMumentHistoryDataSource,
    private val remoteSearchListDataSource: RemoteSearchListDataSource,
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    // Remote
    override suspend fun searchList(keyword: String): Flow<List<RecentSearchData>> = flow {
        remoteSearchListDataSource.searchMusicList(keyword).data?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMumentHistory(
        userId: String,
        musicId: String
    ): Flow<MumentHistoryEntity> =
        flow {
            remoteMumentHistoryDataSource.getMumentHistory(userId, musicId).apply {
                data?.let { emit(MumentHistoryEntity(it.mumentHistory, it.music)) }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getBannerMument(): Flow<BannerMumentDto> = flow {
        homeDataSource.getBannerMument().data?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getKnownMument(): Flow<KnownMumentDto> = flow {
        homeDataSource.getKnownMument().data?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRandomMument(): Flow<RandomMumentDto> = flow {
        homeDataSource.getRandomMument().data?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRemoteTodayMument(userId: String): Flow<TodayMumentDto>? = flow {
        homeDataSource.getTodayMument(userId)?.data?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    // Local
    override suspend fun getLocalTodayMument(userId: String): Flow<TodayMumentEntity> = flow {
        emit(localTodayMumentDataSource.getTodayMument(userId))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateTodayMument(mument: TodayMumentEntity) {
        localTodayMumentDataSource.updateMument(mument)
    }

    override suspend fun insertTodayMument(mument: TodayMumentEntity) {
        localTodayMumentDataSource.insertMument(mument)
    }

    override suspend fun deleteTodayMument(mument: TodayMumentEntity) {
        localTodayMumentDataSource.deleteMument(mument)
    }

    override suspend fun getRecentSearchList(): Flow<List<RecentSearchData>> = flow {
        emit(localRecentSaerchListDataSource.getAllRecentSearchList())
    }.flowOn(Dispatchers.IO)


    override suspend fun insertRecentSearchList(data: RecentSearchData) {
        localRecentSaerchListDataSource.insertRecentSearchList(data)
    }

    override suspend fun updateRecentSearchList(data: RecentSearchData) {
        localRecentSaerchListDataSource.updateRecentSearchList(
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
        localRecentSaerchListDataSource.deleteRecentSearchList(data)
    }

    override suspend fun deleteAllRecentSearchList() {
        localRecentSaerchListDataSource.deleteAllRecentSearchList()
    }

}