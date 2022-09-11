package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.home.*
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.mapper.home.RandomMumentMapper
import com.mument_android.app.domain.entity.history.MumentHistoryEntity
import com.mument_android.app.domain.entity.home.*
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localTodayMumentDataSource: LocalTodayMumentDataSource,
    private val localRecentSaerchListDataSource: LocalRecentSearchListDataSource,
    private val remoteMumentHistoryDataSource: RemoteMumentHistoryDataSource,
    private val remoteSearchListDataSource: RemoteSearchListDataSource,
    private val homeDataSource: HomeDataSource,
    private val randomMumentMapper: RandomMumentMapper
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

    override suspend fun getBannerMument(): Flow<List<BannerEntity>> = flow {
        homeDataSource.getBannerMument().data?.let { emit(it.bannerList) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getKnownMument(): Flow<List<AgainMumentEntity>> = flow {
        homeDataSource.getKnownMument().data?.let { emit(it.againMumentEntity) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRandomMument(): Flow<RandomMumentEntity> = flow {
        homeDataSource.getRandomMument().data?.let { emit(randomMumentMapper.map(it)) }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRemoteTodayMument(userId: String): Flow<TodayMumentEntity> = flow {
        homeDataSource.getTodayMument(userId)?.data?.let { emit(it.todayMument) }
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