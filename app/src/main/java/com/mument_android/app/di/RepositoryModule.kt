package com.mument_android.app.di

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.datasource.home.*
import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.datasource.record.RecordDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.mapper.locker.LockerMapper
import com.mument_android.app.data.mapper.record.RecordMapper
import com.mument_android.app.data.repository.HomeRepositoryImpl
import com.mument_android.app.data.repository.LockerRepositoryImpl
import com.mument_android.app.data.repository.MumentDetailRepositoryImpl
import com.mument_android.app.data.repository.RecordRepositoryImpl
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
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
        homeDataSource: HomeDataSource
    ): HomeRepository = HomeRepositoryImpl(
        todayMumentDataSource,
        recentSearchListDataSource,
        mumentHistoryDataSource,
        searchListDataSource, homeDataSource
    )

}