package com.mument_android.app.di

import com.startup.core_dependent.MumentTagListAdapter
import com.startup.data.controller.DeleteMumentController
import com.startup.data.controller.LikeMumentController
import com.startup.data.controller.RecordController
import com.startup.data.controller.RecordModifyController
import com.startup.data.datasource.detail.MumentDetailDataSource
import com.startup.data.datasource.detail.MumentListDataSource
import com.startup.data.datasource.detail.MusicDetailDataSource
import com.startup.data.datasource.locker.LockerDataSource
import com.startup.data.datasource.record.RecordDataSource
import com.startup.data.mapper.album.MusicWithMyMumentMapper
import com.startup.data.mapper.detail.MumentDetailMapper
import com.startup.data.mapper.detail.MumentSummaryMapper
import com.startup.data.mapper.home.RandomMumentMapper
import com.startup.data.mapper.locker.LockerMapper
import com.startup.data.mapper.record.MumentRecordMapper
import com.startup.data.mapper.record.RecordMapper
import com.startup.data.datasource.home.*
import com.startup.data.repository.*
import com.startup.domain.repository.detail.MumentDetailRepository
import com.startup.domain.repository.detail.MumentListRepository
import com.startup.domain.repository.detail.MusicDetailRepository
import com.startup.domain.repository.home.HomeRepository
import com.startup.domain.repository.locker.LockerRepository
import com.startup.domain.repository.main.MainRepository
import com.startup.domain.repository.record.RecordRepository
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
    fun provideMumentMainUseCase(
        likeMumentController: LikeMumentController
    ): MainRepository =
        MainRepositoryImpl(
            likeMumentController
        )

    @Provides
    @Singleton
    fun provideMumentDetailUseCase(
        mumentDetailDataSource: MumentDetailDataSource,
        mumentDetailMapper: MumentDetailMapper,
        deleteMumentController: DeleteMumentController
    ): MumentDetailRepository =
        MumentDetailRepositoryImpl(
            mumentDetailDataSource,
            mumentDetailMapper,
            deleteMumentController
        )

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
        recordDataSource: RecordDataSource,
        recordModifyController: RecordModifyController,
        recordController: RecordController,
        mumentRecordMapper: MumentRecordMapper
    ): RecordRepository = RecordRepositoryImpl(
        recordDataSource,
        recordMapper,
        recordModifyController,
        recordController,
        mumentRecordMapper
    )


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