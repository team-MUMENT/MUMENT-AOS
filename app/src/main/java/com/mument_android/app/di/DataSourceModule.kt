package com.mument_android.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.datasource.locker.LockerDataSourceImpl
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.datasource.record.RecordDataSourceImpl
import com.mument_android.data.datasource.detail.*
import com.mument_android.data.datasource.home.*
import com.mument_android.data.datasource.mypage.BlockUserListDataSource
import com.mument_android.data.datasource.mypage.BlockUserListDataSourceImpl
import com.mument_android.data.datasource.mypage.NoticeListDataSource
import com.mument_android.data.datasource.mypage.NoticeListDataSourceImpl
import com.mument_android.data.local.MumentDatabase
import com.mument_android.data.local.converter.DateTypeConverter
import com.mument_android.data.local.converter.IntListTypeConverter
import com.mument_android.data.local.recentlist.RecentSearchDAO
import com.mument_android.data.local.todaymument.TodayMumentDAO
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.network.locker.LockerApiService
import com.mument_android.data.network.mypage.MyPageApiService
import com.mument_android.data.network.record.RecordApiService
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

    @Provides
    @Singleton
    fun provideBlockUserDataSource(detailApiService: DetailApiService): BlockUserDataSource =
        BlockUserDataSourceImpl(detailApiService)

    @Provides
    @Singleton
    fun provideBlockUserIstDataSource(myPageApiService: MyPageApiService): BlockUserListDataSource =
        BlockUserListDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideNoticeListDataSource(myPageApiService: MyPageApiService): NoticeListDataSource =
        NoticeListDataSourceImpl(myPageApiService)
}