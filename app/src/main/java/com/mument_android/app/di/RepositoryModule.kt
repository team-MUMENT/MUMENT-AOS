package com.mument_android.app.di

import com.mument_android.core_dependent.ext.DataStoreManager
import com.mument_android.data.controller.*
import com.mument_android.data.datasource.app.LimitUserDataSource
import com.mument_android.data.datasource.detail.*
import com.mument_android.data.datasource.home.*
import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.datasource.mypage.*
import com.mument_android.data.datasource.notify.NotifyDataSource
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.data.mapper.app.LimitUserMapper
import com.mument_android.data.mapper.detail.MumentDetailMapper
import com.mument_android.data.mapper.detail.MumentSummaryMapper
import com.mument_android.data.mapper.detail.ReportMumentMapper
import com.mument_android.data.mapper.home.HomeTodayMumentMapper
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.mapper.home.RecentSearchDataMapper
import com.mument_android.data.mapper.locker.LockerMapper
import com.mument_android.data.mapper.mypage.*
import com.mument_android.data.mapper.notify.NotifyMapper
import com.mument_android.data.mapper.record.MumentModifyMapper
import com.mument_android.data.mapper.record.MumentRecordMapper
import com.mument_android.data.mapper.record.RecordMapper
import com.mument_android.data.mapper.sign.*
import com.mument_android.data.network.detail.DetailApiService
import com.mument_android.data.repository.*
import com.mument_android.data.repository.mypage.*
import com.mument_android.domain.repository.app.LimitUserRepository
import com.mument_android.domain.repository.detail.*
import com.mument_android.domain.repository.home.HomeRepository
import com.mument_android.domain.repository.locker.LockerRepository
import com.mument_android.domain.repository.main.LikeMumentRepository
import com.mument_android.domain.repository.mypage.*
import com.mument_android.domain.repository.notify.NotifyRepository
import com.mument_android.domain.repository.record.RecordRepository
import com.mument_android.domain.repository.sign.SignRepository
import com.mument_android.domain.util.ErrorHandler
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
        likeMumentController: LikeMumentDataSource
    ): LikeMumentRepository =
        LikeMumentRepositoryImpl(
            likeMumentController
        )

    @Provides
    @Singleton
    fun provideMumentDetailUseCase(
        mumentDetailDataSource: MumentDetailDataSource,
        mumentDetailMapper: MumentDetailMapper,
        deleteMumentController: DeleteMumentController,
        historyDataSource: HistoryDataSource,
        errorHandler: ErrorHandler,
        reportMumentDataSource: ReportMumentDataSource,
        reportMumentMapper: ReportMumentMapper
    ): MumentDetailRepository =
        MumentDetailRepositoryImpl(
            mumentDetailDataSource,
            mumentDetailMapper,
            deleteMumentController,
            historyDataSource,
            errorHandler,
            reportMumentDataSource,
            reportMumentMapper
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
        mumentRecordMapper: MumentRecordMapper,
        mumentModifyMapper: MumentModifyMapper
    ): RecordRepository = RecordRepositoryImpl(
        recordDataSource,
        recordMapper,
        recordModifyController,
        recordController,
        mumentRecordMapper,
        mumentModifyMapper
    )


    @Provides
    @Singleton
    fun provideHomeRepository(
        todayMumentDataSource: LocalTodayMumentDataSource,
        recentSearchListDataSource: LocalRecentSearchListDataSource,
        searchListDataSource: RemoteSearchListDataSource,
        homeDataSource: HomeDataSource,
        randomMumentMapper: RandomMumentMapper,
        homeTodayMumentMapper: HomeTodayMumentMapper,
        recentSearchDataMapper: RecentSearchDataMapper,
        dataStoreManager: DataStoreManager
    ): HomeRepository = HomeRepositoryImpl(
        todayMumentDataSource,
        recentSearchListDataSource,
        searchListDataSource,
        homeDataSource,
        randomMumentMapper,
        homeTodayMumentMapper,
        recentSearchDataMapper,
        dataStoreManager
    )

    @Provides
    @Singleton
    fun provideNotifyRepository(
        notifyDataSource: NotifyDataSource,
        notifyMapper: NotifyMapper
    ): NotifyRepository = NotifyRepositoryImpl(notifyDataSource, notifyMapper)

    @Provides
    @Singleton
    fun provideMusicDetailRepository(
        musicWithMyMumentMapper: MusicWithMyMumentMapper,
        errorHandler: ErrorHandler,
        musicDetailDataSource: MusicDetailDataSource
    ): MusicDetailRepository =
        MusicDetailRepositoryImpl(musicWithMyMumentMapper, errorHandler, musicDetailDataSource)

    @Provides
    @Singleton
    fun provideMumentListRepository(
        mumentSummaryMapper: MumentSummaryMapper,
        mumentListDataSource: MumentListDataSource
    ): MumentListRepository = MumentListRepositoryImpl(mumentSummaryMapper, mumentListDataSource)

    @Provides
    @Singleton
    fun provideSignRepository(
        signMapper: SignMapper,
        signDataSource: SignDataSource,
        requestSetProfileMapper: RequestSetProfileMapper,
        setProfileMapper: SetProfileMapper,
        kakaoLoginMapper: KakaoLoginMapper,
        getWebViewMapper: GetWebViewMapper,
        newTokenMapper: NewTokenMapper
    ): SignRepository = SignRepositoryImpl(
        signDataSource,
        setProfileMapper,
        kakaoLoginMapper,
        requestSetProfileMapper,
        getWebViewMapper,
        newTokenMapper
    )

    @Provides
    @Singleton
    fun provideBlockUserRepository(
        errorHandler: ErrorHandler,
        blockUserDataSource: BlockUserDataSource
    ): BlockUserRepository = BlockUserRepositoryImpl(blockUserDataSource, errorHandler)

    @Provides
    @Singleton
    fun provideBlockUserListRepository(
        blockUserListDataSource: BlockUserListDataSource,
        blockUserListMapper: BlockUserListMapper,
        deleteBlockUserController: DeleteBlockUserController
    ): BlockUserListRepository =
        BlockUserListRepositoryImpl(
            blockUserListDataSource,
            blockUserListMapper,
            deleteBlockUserController
        )

    @Provides
    @Singleton
    fun provideNoticeListRepository(
        noticeDetailDataSource: NoticeDetailDataSource,
        noticeListDataSource: NoticeListDataSource,
        noticeListMapper: NoticeListMapper
    ): NoticeListRepository =
        NoticeListRepositoryImpl(noticeDetailDataSource, noticeListDataSource, noticeListMapper)

    @Provides
    @Singleton
    fun provideUserInfoRepository(
        userInfoDataSource: UserInfoDataSource,
        userInfoMapper: UserInfoMapper
    ): UserInfoRepository = UserInfoRepositoryImpl(userInfoDataSource, userInfoMapper)


    @Provides
    @Singleton
    fun provideLimitUserRepository(
        limitUserDataSource: LimitUserDataSource,
        limitUserMapper: LimitUserMapper
    ): LimitUserRepository =
        LimitUserRepositoryImpl(
            limitUserDataSource,
            limitUserMapper
        )

    @Provides
    @Singleton
    fun provideUsersLikeMumentRepository(
        detailApiService: DetailApiService
    ): UsersRepository = UsersRepositoryImpl(detailApiService)

    @Provides
    @Singleton
    fun provideUnregisterRepository(
        unregisterDataSource: UnregisterDataSource,
        userLocalDataSource: UserLocalDataSource,
        unregisterMapper: RequestUnregisterMapper
    ): UnregisterRepository = UnregisterRepositoryImpl(
        unregisterDataSource, userLocalDataSource, unregisterMapper
    )

    @Provides
    @Singleton
    fun provideUnregisterReasonRepository(
        unregisterReasonDataSource: UnregisterReasonDataSource,
        unregisterReasonMapper: UnregisterReasonMapper,
        requestUnregisterReasonMapper: RequestUnregisterReasonMapper
    ): UnregisterReasonRepository = UnregisterReasonRepositoryImpl(
        unregisterReasonDataSource, unregisterReasonMapper, requestUnregisterReasonMapper
    )

    @Provides
    @Singleton
    fun provideLogOutRepository(
        logOutDataSource: LogOutDataSource
    ): LogOutRepository = LogOutRepositoryImpl(
        logOutDataSource
    )
}
