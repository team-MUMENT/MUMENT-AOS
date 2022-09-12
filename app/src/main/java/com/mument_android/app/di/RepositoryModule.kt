package com.mument_android.app.di

import com.mument_android.app.data.controller.DeleteMumentController
import com.mument_android.app.data.controller.LikeMumentController
import com.mument_android.app.data.controller.RecordController
import com.mument_android.app.data.controller.RecordModifyController
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
import com.mument_android.app.data.mapper.record.MumentRecordMapper
import com.mument_android.app.data.mapper.record.RecordMapper
import com.mument_android.app.data.repository.*
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