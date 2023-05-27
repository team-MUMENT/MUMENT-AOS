package com.mument_android.app.di

import com.mument_android.core_dependent.network.TokenDataSource
import com.mument_android.core_dependent.network.TokenDataSourceImpl
import com.mument_android.data.datasource.app.LimitUserDataSource
import com.mument_android.data.datasource.app.LimitUserDataSourceImpl
import com.mument_android.data.datasource.detail.*
import com.mument_android.data.datasource.home.*
import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.datasource.locker.LockerDataSourceImpl
import com.mument_android.data.datasource.mypage.*
import com.mument_android.data.datasource.notify.NotifyDataSource
import com.mument_android.data.datasource.notify.NotifyDataSourceImpl
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.datasource.record.RecordDataSourceImpl
import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.datasource.sign.SignDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindIsFirstDataSource(recordDataSourceImpl: RecordDataSourceImpl): RecordDataSource

    @Binds
    @Singleton
    abstract fun bindMumentDetailDataSource(mumentDetailDataSourceImpl: MumentDetailDataSourceImpl): MumentDetailDataSource

    @Binds
    @Singleton
    abstract fun bindLockerDataSource(lockerDataSourceImpl: LockerDataSourceImpl): LockerDataSource

    @Binds
    @Singleton
    abstract fun bindSignDataSource(signDataSourceImpl: SignDataSourceImpl): SignDataSource

    @Binds
    @Singleton
    abstract fun bindHistoryDataSource(historyDataSourceImpl: HistoryDataSourceImpl): HistoryDataSource


    @Binds
    @Singleton
    abstract fun bindRecentSearchListDataSource(localRecentSearchListDataSourceImpl: LocalRecentSearchListDataSourceImpl): LocalRecentSearchListDataSource

    @Binds
    @Singleton
    abstract fun bindTodayMumentDataSource(localTodayMumentDataSourceImpl: LocalTodayMumentDataSourceImpl): LocalTodayMumentDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSearchListDataSource(remoteSearchListDataSourceImpl: RemoteSearchListDataSourceImpl): RemoteSearchListDataSource

    @Binds
    @Singleton
    abstract fun bindNotifyDataSource(notifyDataSourceImpl: NotifyDataSourceImpl): NotifyDataSource

    @Binds
    @Singleton
    abstract fun bindHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource


    @Binds
    @Singleton
    abstract fun bindMusicDetailDataSource(musicDetailDataSourceImpl: MusicDetailDataSourceImpl): MusicDetailDataSource

    @Binds
    @Singleton
    abstract fun bindMumentListDataSource(mumentListDataSourceImpl: MumentListDataSourceImpl): MumentListDataSource

    @Binds
    @Singleton
    abstract fun bindBlockUserListDataSource(blockUserListDataSourceImpl: BlockUserListDataSourceImpl): BlockUserListDataSource

    @Binds
    @Singleton
    abstract fun bindBlockUserDataSource(blockUserDataSourceImpl: BlockUserDataSourceImpl): BlockUserDataSource

    @Binds
    @Singleton
    abstract fun bindNoticeListDataSource(noticeListDataSourceImpl: NoticeListDataSourceImpl): NoticeListDataSource

    @Binds
    @Singleton
    abstract fun bindNoticeDetailDataSource(noticeDetailDataSourceImpl: NoticeDetailDataSourceImpl): NoticeDetailDataSource

    @Binds
    @Singleton
    abstract fun bindUserInfoDataSource(userInfoDataSourceImpl: UserInfoDataSourceImpl): UserInfoDataSource

    @Binds
    @Singleton
    abstract fun bindLimitUserDataSource(limitUserDataSourceImpl: LimitUserDataSourceImpl): LimitUserDataSource

    @Binds
    @Singleton
    abstract fun bindTokenDataSource(tokenDataSourceImpl: TokenDataSourceImpl): TokenDataSource

    @Binds
    @Singleton
    abstract fun bindUnregisterDataSource(unregisterDataSourceImpl: UnregisterDataSourceImpl): UnregisterDataSource

    @Binds
    @Singleton
    abstract fun bindUnregisterReasonDataSource(unregisterReasonDataSourceImpl: UnregisterReasonDataSourceImpl): UnregisterReasonDataSource

    @Binds
    @Singleton
    abstract fun bindReportMumentDataSource(reportMumentDataSourceImpl: ReportMumentDataSourceImpl): ReportMumentDataSource

    @Binds
    @Singleton
    abstract fun bindLogOutDataSource(logOutDataSourceImpl: LogOutDataSourceImpl): LogOutDataSource
}