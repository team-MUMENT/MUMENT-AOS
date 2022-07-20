package com.mument_android.app.di

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.datasource.home.LocalRecentSearchListDataSource
import com.mument_android.app.data.datasource.home.LocalTodayMumentDataSource
import com.mument_android.app.data.datasource.home.RemoteMumentHistoryDataSource
import com.mument_android.app.data.datasource.home.RemoteSearchListDataSource
import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.mapper.locker.LockerMapper
import com.mument_android.app.data.repository.HomeRepositoryImpl
import com.mument_android.app.data.repository.LockerRepositoryImpl
import com.mument_android.app.data.repository.MumentDetailRepositoryImpl
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import com.mument_android.app.domain.repository.home.HomeRepository
import com.mument_android.app.domain.repository.locker.LockerRepository
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
    fun provideHomeRepository(
        todayMumentDataSource: LocalTodayMumentDataSource,
        recentSearchListDataSource: LocalRecentSearchListDataSource,
        mumentHistoryDataSource: RemoteMumentHistoryDataSource,
        searchListDataSource: RemoteSearchListDataSource,
    ): HomeRepository = HomeRepositoryImpl(
        todayMumentDataSource,
        recentSearchListDataSource,
        mumentHistoryDataSource,
        searchListDataSource
    )

}