package com.mument_android.app.data.repository

import com.mument_android.app.data.datasource.home.LocalRecentSearchListDataSource
import com.mument_android.app.data.datasource.home.LocalTodayMumentDataSource
import com.mument_android.app.data.datasource.home.RemoteMumentHistoryDataSource
import com.mument_android.app.data.datasource.home.RemoteSearchListDataSource
import com.mument_android.app.data.dto.history.MumentHistoryDto
import com.mument_android.app.data.local.recentlist.RecentSearchData
import com.mument_android.app.data.local.todaymument.TodayMumentEntity
import com.mument_android.app.domain.repository.home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localTodayMumentDataSource: LocalTodayMumentDataSource,
    private val localRecentSaerchListDataSource: LocalRecentSearchListDataSource,
    private val remoteMumentHistoryDataSource: RemoteMumentHistoryDataSource,
    private val remoteSearchListDataSource: RemoteSearchListDataSource,
    ) : HomeRepository {
    // Remote
    override suspend fun searchList(keyword: String): Flow<List<RecentSearchData>> = flow {
        remoteSearchListDataSource.searchMusicList(keyword).apply {
            if(this.success){
                emit(this.data)
            }else{
                Timber.d("Fail Get Search List")
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMumentHistory(userId: String, musicId: String): Flow<MumentHistoryDto> = flow<MumentHistoryDto> {
        remoteMumentHistoryDataSource.getMumentHistory(userId, musicId).apply {
            if(this.success){
                emit(this.data)
            }else{
                Timber.d("Fail Get Mument History")
            }
        }
    }.flowOn(Dispatchers.IO)

    // Local
    override suspend fun getTodayMument(): List<TodayMumentEntity> =
        localTodayMumentDataSource.getTodayMument()


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