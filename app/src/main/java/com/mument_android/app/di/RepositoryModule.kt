package com.mument_android.app.di

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.datasource.detail.MumentListDataSource
import com.mument_android.app.data.datasource.detail.MusicDetailDataSource
import com.mument_android.app.data.datasource.home.*
import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.datasource.record.RecordDataSource
import com.mument_android.app.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.mapper.detail.MumentSummaryMapper
import com.mument_android.app.data.mapper.home.RandomMumentMapper
import com.mument_android.app.data.mapper.locker.LockerMapper
import com.mument_android.app.data.mapper.record.RecordMapper
import com.mument_android.app.data.repository.*
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import com.mument_android.app.domain.repository.detail.MumentListRepository
import com.mument_android.app.domain.repository.detail.MusicDetailRepository
import com.mument_android.app.domain.repository.home.HomeRepository
import com.mument_android.app.domain.repository.locker.LockerRepository
import com.mument_android.app.domain.repository.record.RecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMumentDetailUseCase(
        mumentDetailDataSource: MumentDetailDataSource,
        mumentDetailMapper: MumentDetailMapper
    ): MumentDetailRepository =
        MumentDetailRepositoryImpl(mumentDetailDataSource, mumentDetailMapper)

    @Provides
    @Singleton
    fun provideLockerRepository(
        lockerMapper: LockerMapper,
        lockerDataSource: LockerDataSource
    ): LockerRepository = LockerRepositoryImpl(lockerMapper, lockerDataSource)

    @Provides
    @Singleton
    fun provideRecordRepository(
        recordMapper: RecordMapper,
        recordDataSource: RecordDataSource
    ): RecordRepository = RecordRepositoryImpl(recordDataSource, recordMapper)


    @Provides
    @Singleton
    fun provideHomeRepository(
        todayMumentDataSource: LocalTodayMumentDataSource,
        recentSearchListDataSource: LocalRecentSearchListDataSource,
        mumentHistoryDataSource: RemoteMumentHistoryDataSource,
        searchListDataSource: RemoteSearchListDataSource,
        homeDataSource: HomeDataSource,
        randomMumentMapper: RandomMumentMapper
    ): HomeRepository = HomeRepositoryImpl(
        todayMumentDataSource,
        recentSearchListDataSource,
        mumentHistoryDataSource,
        searchListDataSource, homeDataSource, randomMumentMapper
    )

    @Provides
    @Singleton
    fun provideMusicDetailRepository(
        musicWithMyMumentMapper: MusicWithMyMumentMapper,
        musicDetailDataSource: MusicDetailDataSource
    ): MusicDetailRepository =
        MusicDetailRepositoryImpl(musicWithMyMumentMapper, musicDetailDataSource)

    @Provides
    @Singleton
    fun provideMumentListRepository(
        mumentSummaryMapper: MumentSummaryMapper,
        mumentListDataSource: MumentListDataSource
    ): MumentListRepository = MumentListRepositoryImpl(mumentSummaryMapper, mumentListDataSource)
}