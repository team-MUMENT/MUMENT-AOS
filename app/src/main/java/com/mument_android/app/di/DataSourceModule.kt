package com.mument_android.app.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mument_android.data.datasource.app.LimitUserDataSource
import com.mument_android.data.datasource.app.LimitUserDataSourceImpl
import com.mument_android.core_dependent.network.RefreshTokenApiService
import com.mument_android.core_dependent.network.TokenDataSource
import com.mument_android.core_dependent.network.TokenDataSourceImpl
import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.datasource.locker.LockerDataSourceImpl
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.datasource.record.RecordDataSourceImpl
import com.mument_android.data.datasource.detail.*
import com.mument_android.data.datasource.home.*
import com.mument_android.data.datasource.notify.NotifyDataSource
import com.mument_android.data.datasource.notify.NotifyDataSourceImpl
import com.mument_android.data.local.MumentDatabase
import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.datasource.sign.SignDataSourceImpl
import com.mument_android.data.datasource.mypage.*
import com.mument_android.data.local.converter.DateTypeConverter
import com.mument_android.data.local.converter.IntListTypeConverter
import com.mument_android.data.local.recentlist.RecentSearchDAO
import com.mument_android.data.local.todaymument.TodayMumentDAO
import com.mument_android.data.network.app.AppApiService
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.network.detail.HistoryService
import com.mument_android.data.network.home.HomeService
import com.mument_android.data.network.home.NotifyService
import com.mument_android.data.network.locker.LockerApiService
import com.mument_android.data.network.mypage.MyPageApiService
import com.mument_android.data.network.record.RecordApiService
import com.mument_android.data.network.sign.SignApiService
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
    fun provideSignDataSource(signApiService: SignApiService): SignDataSource =
        SignDataSourceImpl(signApiService)

    @Provides
    @Singleton
    fun provideHistoryDataSource(historyService: HistoryService): HistoryDataSource =
        HistoryDataSourceImpl(historyService)

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
    fun provideUserLocalDataSource(
        todayMumentDAO: TodayMumentDAO,
        recentSearchDAO: RecentSearchDAO
    ): UserLocalDataSource =
        UserLocalDataSourceImpl(todayDao = todayMumentDAO, recentSearchDAO = recentSearchDAO)

    @Provides
    @Singleton
    fun provideSearchListDataSource(service: HomeService): RemoteSearchListDataSource =
        RemoteSearchListDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideNotifyDataSource(service: NotifyService): NotifyDataSource =
        NotifyDataSourceImpl(service)

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
    fun provideBlockUserListDataSource(myPageApiService: MyPageApiService): BlockUserListDataSource =
        BlockUserListDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideNoticeListDataSource(myPageApiService: MyPageApiService): NoticeListDataSource =
        NoticeListDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideNoticeDetailDataSource(myPageApiService: MyPageApiService): NoticeDetailDataSource =
        NoticeDetailDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideUserInfoDataSource(myPageApiService: MyPageApiService): UserInfoDataSource =
        UserInfoDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideLimitUserDataSource(appApiService: AppApiService): LimitUserDataSource =
        LimitUserDataSourceImpl(appApiService)

    @Provides
    @Singleton
    fun provideTokenDataSource(refreshTokenApiService: RefreshTokenApiService): TokenDataSource =
        TokenDataSourceImpl(refreshTokenApiService)

    @Provides
    @Singleton
    fun provideUnregisterDataSource(myPageApiService: MyPageApiService): UnregisterDataSource =
        UnregisterDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideUnregisterReasonDataSource(myPageApiService: MyPageApiService): UnregisterReasonDataSource =
        UnregisterReasonDataSourceImpl(myPageApiService)

    @Provides
    @Singleton
    fun provideReportMumentDataSource(detailApiService: DetailApiService): ReportMumentDataSource =
        ReportMumentDataSourceImpl(detailApiService)

    @Provides
    @Singleton
    fun provideLogOutDataSource(myPageApiService: MyPageApiService): LogOutDataSource =
        LogOutDataSourceImpl(myPageApiService)
}