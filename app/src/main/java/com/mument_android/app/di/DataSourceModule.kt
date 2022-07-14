package com.mument_android.app.di

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.datasource.detail.MumentDetailDataSourceImpl
import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.datasource.locker.LockerDataSourceImpl
import com.mument_android.app.data.network.detail.DetailApiService
import com.mument_android.app.data.network.locker.LockerNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMumentDetailDataSource(detailApiService: DetailApiService): MumentDetailDataSource =
        MumentDetailDataSourceImpl(detailApiService)

    @Provides
    @Singleton
    fun provideLockerDataSource(lockerNetwork: LockerNetwork): LockerDataSource =
        LockerDataSourceImpl(lockerNetwork)
}