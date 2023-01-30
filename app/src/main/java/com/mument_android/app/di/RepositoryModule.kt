package com.mument_android.app.di

import com.mument_android.data.controller.DeleteMumentController
import com.mument_android.data.controller.LikeMumentController
import com.mument_android.data.controller.RecordController
import com.mument_android.data.controller.RecordModifyController
import com.mument_android.data.datasource.detail.BlockUserDataSource
import com.mument_android.data.datasource.detail.MumentDetailDataSource
import com.mument_android.data.datasource.detail.MumentListDataSource
import com.mument_android.data.datasource.detail.MusicDetailDataSource
import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.data.mapper.detail.MumentDetailMapper
import com.mument_android.data.mapper.detail.MumentSummaryMapper
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.mapper.locker.LockerMapper
import com.mument_android.data.mapper.record.MumentRecordMapper
import com.mument_android.data.mapper.record.RecordMapper
import com.mument_android.data.datasource.home.*
import com.mument_android.data.mapper.detail.BlockUserMapper
import com.mument_android.data.mapper.home.HomeTodayMumentMapper
import com.mument_android.data.mapper.home.RecentSearchDataMapper
import com.mument_android.data.repository.*
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.repository.detail.MumentListRepository
import com.mument_android.domain.repository.detail.MusicDetailRepository
import com.mument_android.domain.repository.home.HomeRepository
import com.mument_android.domain.repository.locker.LockerRepository
import com.mument_android.domain.repository.main.MainRepository
import com.mument_android.domain.repository.record.RecordRepository
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
        randomMumentMapper: RandomMumentMapper,
        homeTodayMumentMapper: HomeTodayMumentMapper,
        recentSearchDataMapper: RecentSearchDataMapper
    ): HomeRepository = HomeRepositoryImpl(
        todayMumentDataSource,
        recentSearchListDataSource,
        mumentHistoryDataSource,
        searchListDataSource,
        homeDataSource,
        randomMumentMapper,
        homeTodayMumentMapper,
        recentSearchDataMapper
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

    @Provides
    @Singleton
    fun provideBlockUserRepository(
        blockUserDataSource: BlockUserDataSource,
        blockUserMapper: BlockUserMapper
    ): BlockUserRepository = BlockUserRepositoryImpl(blockUserDataSource, blockUserMapper)
}