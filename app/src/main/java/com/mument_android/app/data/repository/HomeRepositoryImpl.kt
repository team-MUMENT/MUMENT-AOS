package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.home.*
import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.dto.home.BannerMumentDto
import com.mument_android.app.data.dto.home.KnownMumentDto
import com.mument_android.app.data.dto.home.RandomMumentDto
import com.mument_android.app.data.dto.home.TodayMumentDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
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
        emit(remoteSearchListDataSource.searchMusicList(keyword).data)
    }.flowOn(Dispatchers.IO)

    override suspend fun getMumentHistory(userId: String, musicId: String): Flow<MumentHistoryDto> =
        flow<MumentHistoryDto> {
            remoteMumentHistoryDataSource.getMumentHistory(userId, musicId).apply {
                if (this.success) {
                    emit(this.data)
                } else {
                    Timber.d("Fail Get Mument History")
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getBannerMument(): Flow<BannerMumentDto> = flow {
        emit(homeDataSource.getBannerMument().data)
    }.flowOn(Dispatchers.IO)

    override suspend fun getKnownMument(): Flow<KnownMumentDto> = flow {
        emit(homeDataSource.getKnownMument().data)
    }.flowOn(Dispatchers.IO)

    override suspend fun getRandomMument(): Flow<RandomMumentDto> = flow {
        emit(homeDataSource.getRandomMument().data)
    }.flowOn(Dispatchers.IO)

    override suspend fun getRemoteTodayMument(userId: String): Flow<TodayMumentDto> = flow {
        emit(homeDataSource.getTodayMument(userId).data)
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