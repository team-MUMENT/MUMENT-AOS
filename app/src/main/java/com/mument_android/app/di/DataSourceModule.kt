package com.mument_android.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.datasource.detail.MumentDetailDataSourceImpl
import com.mument_android.app.data.datasource.home.RecentSearchListDataSource
import com.mument_android.app.data.datasource.home.RecentSearchListDataSourceImpl
import com.mument_android.app.data.datasource.home.TodayMumentDataSource
import com.mument_android.app.data.datasource.home.TodayMumentDataSourceImpl
import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.datasource.locker.LockerDataSourceImpl
import com.mument_android.app.data.datasource.record.RecordDataSource
import com.mument_android.app.data.datasource.record.RecordDataSourceImpl
import com.mument_android.app.data.local.converter.DateTypeConverter
import com.mument_android.app.data.local.converter.IntListTypeConverter
import com.mument_android.app.data.local.converter.MusicTypeConverter
import com.mument_android.app.data.local.converter.UserTypeConverter
import com.mument_android.app.data.local.recentlist.RecentSearchDAO
import com.mument_android.app.data.local.todaymument.MumentDatabase
import com.mument_android.app.data.local.todaymument.TodayMumentDAO
import com.mument_android.app.data.network.detail.DetailApiService
import com.mument_android.app.data.network.locker.LockerApiService
import com.mument_android.app.data.network.record.RecordApiService
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
    fun provideGson(): Gson {
        return Gson()
    }

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

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, gson: Gson): MumentDatabase {
        return Room.databaseBuilder(
            context,
            MumentDatabase::class.java,
            "friend_data_database"
        )
            .addTypeConverter(IntListTypeConverter(gson))
            .addTypeConverter(UserTypeConverter(gson))
            .addTypeConverter(MusicTypeConverter(gson))
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
    fun provideRecentSearchListDataSource(dao: RecentSearchDAO): RecentSearchListDataSource =
        RecentSearchListDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideTodayMumentDataSource(dao: TodayMumentDAO): TodayMumentDataSource =
        TodayMumentDataSourceImpl(dao)
}