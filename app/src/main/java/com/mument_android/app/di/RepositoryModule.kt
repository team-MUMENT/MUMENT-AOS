package com.mument_android.app.di

import com.mument_android.data.controller.*
import com.mument_android.data.datasource.app.LimitUserDataSource
import com.mument_android.data.datasource.detail.BlockUserDataSource
import com.mument_android.data.datasource.detail.MumentDetailDataSource
import com.mument_android.data.datasource.detail.MumentListDataSource
import com.mument_android.data.datasource.detail.MusicDetailDataSource
import com.mument_android.data.datasource.home.HomeDataSource
import com.mument_android.data.datasource.home.LocalRecentSearchListDataSource
import com.mument_android.data.datasource.home.LocalTodayMumentDataSource
import com.mument_android.data.datasource.home.RemoteSearchListDataSource
import com.mument_android.data.datasource.locker.LockerDataSource
import com.mument_android.data.datasource.mypage.BlockUserListDataSource
import com.mument_android.data.datasource.mypage.NoticeDetailDataSource
import com.mument_android.data.datasource.mypage.NoticeListDataSource
import com.mument_android.data.datasource.mypage.UserInfoDataSource
import com.mument_android.data.datasource.notify.NotifyDataSource
import com.mument_android.data.datasource.record.RecordDataSource
import com.mument_android.data.datasource.sign.SignDataSource
import com.mument_android.data.mapper.album.MusicWithMyMumentMapper
import com.mument_android.data.mapper.app.LimitUserMapper
import com.mument_android.data.mapper.detail.BlockUserMapper
import com.mument_android.data.mapper.detail.MumentDetailMapper
import com.mument_android.data.mapper.detail.MumentSummaryMapper
import com.mument_android.data.mapper.home.HomeTodayMumentMapper
import com.mument_android.data.mapper.home.RandomMumentMapper
import com.mument_android.data.mapper.home.RecentSearchDataMapper
import com.mument_android.data.mapper.locker.LockerMapper
import com.mument_android.data.mapper.mypage.BlockUserListMapper
import com.mument_android.data.mapper.mypage.NoticeListMapper
import com.mument_android.data.mapper.mypage.UserInfoMapper
import com.mument_android.data.mapper.notify.NotifyMapper
import com.mument_android.data.mapper.record.MumentRecordMapper
import com.mument_android.data.mapper.record.RecordMapper
import com.mument_android.data.mapper.sign.GetWebViewMapper
import com.mument_android.data.mapper.sign.RequestSetProfileMapper
import com.mument_android.data.mapper.sign.SetProfileMapper
import com.mument_android.data.mapper.sign.SignMapper
import com.mument_android.data.network.detail.HistoryService
import com.mument_android.data.repository.*
import com.mument_android.data.repository.mypage.BlockUserListRepositoryImpl
import com.mument_android.data.repository.mypage.NoticeListRepositoryImpl
import com.mument_android.data.repository.mypage.UserInfoRepositoryImpl
import com.mument_android.domain.repository.app.LimitUserRepository
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.repository.detail.MumentListRepository
import com.mument_android.domain.repository.detail.MusicDetailRepository
import com.mument_android.domain.repository.home.HomeRepository
import com.mument_android.domain.repository.locker.LockerRepository
import com.mument_android.domain.repository.main.MainRepository
import com.mument_android.domain.repository.mypage.BlockUserListRepository
import com.mument_android.domain.repository.mypage.NoticeListRepository
import com.mument_android.domain.repository.mypage.UserInfoRepository
import com.mument_android.domain.repository.notify.NotifyRepository
import com.mument_android.domain.repository.record.RecordRepository
import com.mument_android.domain.repository.sign.SignRepository
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
        likeMumentController: LikeMumentController
    ): MainRepository =
        MainRepositoryImpl(
            likeMumentController
        )

    @Provides
    @Singleton
    fun provideMumentDetailUseCase(
        mumentDetailDataSource: MumentDetailDataSource,
        mumentDetailMapper: MumentDetailMapper,
        deleteMumentController: DeleteMumentController,
        historyService: HistoryService
    ): MumentDetailRepository =
        MumentDetailRepositoryImpl(
            mumentDetailDataSource,
            mumentDetailMapper,
            deleteMumentController,
            historyService
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
        mumentRecordMapper: MumentRecordMapper
    ): RecordRepository = RecordRepositoryImpl(
        recordDataSource,
        recordMapper,
        recordModifyController,
        recordController,
        mumentRecordMapper
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
        recentSearchDataMapper: RecentSearchDataMapper
    ): HomeRepository = HomeRepositoryImpl(
        todayMumentDataSource,
        recentSearchListDataSource,
        searchListDataSource,
        homeDataSource,
        randomMumentMapper,
        homeTodayMumentMapper,
        recentSearchDataMapper
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
        musicDetailDataSource: MusicDetailDataSource
    ): MusicDetailRepository =
        MusicDetailRepositoryImpl(musicWithMyMumentMapper, musicDetailDataSource)

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
        getWebViewMapper: GetWebViewMapper
    ): SignRepository =
        SignRepositoryImpl(signDataSource, signMapper, setProfileMapper, requestSetProfileMapper,getWebViewMapper)

    @Provides
    @Singleton
    fun provideBlockUserRepository(
        blockUserDataSource: BlockUserDataSource,
        blockUserMapper: BlockUserMapper
    ): BlockUserRepository = BlockUserRepositoryImpl(blockUserDataSource, blockUserMapper)

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
    ) : UserInfoRepository = UserInfoRepositoryImpl(userInfoDataSource, userInfoMapper)


    @Provides
    @Singleton
    fun provideLimitUserRepository(
        limitUserDataSource: LimitUserDataSource,
        limitUserMapper: LimitUserMapper
    ) : LimitUserRepository =
        LimitUserRepositoryImpl(
            limitUserDataSource,
            limitUserMapper
        )

}
