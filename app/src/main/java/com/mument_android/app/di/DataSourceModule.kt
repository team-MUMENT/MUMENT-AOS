package com.mument_android.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.startup.data.datasource.locker.LockerDataSource
import com.startup.data.datasource.locker.LockerDataSourceImpl
import com.startup.data.datasource.record.RecordDataSource
import com.startup.data.datasource.record.RecordDataSourceImpl
import com.startup.data.datasource.detail.*
import com.startup.data.datasource.home.*
import com.startup.data.local.MumentDatabase
import com.startup.data.local.converter.DateTypeConverter
import com.startup.data.local.converter.IntListTypeConverter
import com.startup.data.local.recentlist.RecentSearchDAO
import com.startup.data.local.todaymument.TodayMumentDAO
import com.startup.data.network.detail.DetailApiService
import com.startup.data.network.home.HomeService
import com.startup.data.network.locker.LockerApiService
import com.startup.data.network.record.RecordApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideIsFirstDataSource(recordApiService: RecordApiService): RecordDataSource =
        RecordDataSourceImpl(recordApiService)

    @Provides
    @Singleton
    fun provideMumentDetailDataSource(detailApiService: DetailApiService): MumentDetailDataSource =
        MumentDetailDataSourceImpl(detailApiService)

    @Provides
    @Singleton
    fun provideLockerDataSource(lockerNetwork: LockerApiService): LockerDataSource =
        LockerDataSourceImpl(lockerNetwork)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, gson: Gson): MumentDatabase {
        return Room.databaseBuilder(
            context,
            MumentDatabase::class.java,
            "friend_data_database"
        )
            .addTypeConverter(IntListTypeConverter(gson))
            .addTypeConverter(DateTypeConverter())
            .build()
    }

    @Provides
    fun provideTodayDao(database: MumentDatabase): TodayMumentDAO {
        return database.todayMumentDAO()
    }

    @Provides
    fun provideRecentDao(database: MumentDatabase): RecentSearchDAO {
        return database.recentSearchDAO()
    }

    @Provides
    @Singleton
    fun provideRecentSearchListDataSource(dao: RecentSearchDAO): LocalRecentSearchListDataSource =
        LocalRecentSearchListDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideTodayMumentDataSource(dao: TodayMumentDAO): LocalTodayMumentDataSource =
        LocalTodayMumentDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideSearchListDataSource(service: HomeService): RemoteSearchListDataSource =
        RemoteSearchListDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideMumentHistoryDataSource(service: HomeService): RemoteMumentHistoryDataSource =
        RemoteMumentHistoryDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideHomeDataSource(service: HomeService): HomeDataSource =
        HomeDataSourceImpl(service)


    @Provides
    @Singleton
    fun provideMusicDetailDataSource(detailApiService: DetailApiService): MusicDetailDataSource =
        MusicDetailDataSourceImpl(detailApiService)

    @Provides
    @Singleton
    fun provideMumentListDataSource(detailApiService: DetailApiService): MumentListDataSource =
        MumentListDataSourceImpl(detailApiService)
}