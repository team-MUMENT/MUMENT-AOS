package com.mument_android.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.datasource.detail.MumentDetailDataSourceImpl
import com.mument_android.app.data.datasource.home.IntListTypeConverter
import com.mument_android.app.data.datasource.home.MusicTypeConverter
import com.mument_android.app.data.datasource.home.UserTypeConverter
import com.mument_android.app.data.datasource.locker.LockerDataSource
import com.mument_android.app.data.datasource.locker.LockerDataSourceImpl
import com.mument_android.app.data.local.todaymument.TodayMumentDAO
import com.mument_android.app.data.local.todaymument.TodayMumentDatabase
import com.mument_android.app.data.network.detail.DetailApiService
import com.mument_android.app.data.network.locker.LockerNetwork
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
    fun provideMumentDetailDataSource(detailApiService: DetailApiService): MumentDetailDataSource =
        MumentDetailDataSourceImpl(detailApiService)

    @Provides
    @Singleton
    fun provideLockerDataSource(lockerNetwork: LockerNetwork): LockerDataSource =
        LockerDataSourceImpl(lockerNetwork)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, gson: Gson): TodayMumentDatabase {
        return Room.databaseBuilder(
            context,
            TodayMumentDatabase::class.java,
            "friend_data_database")
            .addTypeConverter(IntListTypeConverter(gson))
            .addTypeConverter(UserTypeConverter(gson))
            .addTypeConverter(MusicTypeConverter(gson))
            .build()
    }

    @Provides
    fun provideDao(database: TodayMumentDatabase): TodayMumentDAO {
        return database.todayMumentDAO()
    }
}