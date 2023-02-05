package com.mument_android.app.di

import com.mument_android.domain.repository.mypage.BlockUserListRepository
import com.mument_android.domain.repository.detail.BlockUserRepository
import com.mument_android.domain.repository.detail.MumentDetailRepository
import com.mument_android.domain.repository.detail.MumentListRepository
import com.mument_android.domain.repository.detail.MusicDetailRepository
import com.mument_android.domain.repository.home.HomeRepository
import com.mument_android.domain.repository.locker.LockerRepository
import com.mument_android.domain.repository.main.MainRepository
import com.mument_android.domain.repository.mypage.NoticeListRepository
import com.mument_android.domain.repository.record.RecordRepository
import com.mument_android.domain.repository.sign.SignRepository
import com.mument_android.domain.usecase.detail.*
import com.mument_android.domain.usecase.home.*
import com.mument_android.domain.usecase.locker.FetchMyLikeListUseCase
import com.mument_android.domain.usecase.locker.FetchMyLikeListUseCaseImpl
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCase
import com.mument_android.domain.usecase.locker.FetchMyMumentListUseCaseImpl
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.domain.usecase.main.CancelLikeMumentUseCaseImpl
import com.mument_android.domain.usecase.main.LikeMumentUseCase
import com.mument_android.domain.usecase.main.LikeMumentUseCaseImpl
import com.mument_android.domain.usecase.mypage.*
import com.mument_android.domain.usecase.record.*
import com.mument_android.domain.usecase.sign.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideFetchMumentDetailContentUseCase(mumentDetailRepository: MumentDetailRepository): FetchMumentDetailContentUseCase =
        FetchMumentDetailContentUseCaseImpl(mumentDetailRepository)

    @Provides
    @Singleton
    fun provideIsFirstRecordMumentUseCase(recordRepository: RecordRepository): IsFirstRecordMumentUseCase =
        IsFirstRecordMumentUseCaseImpl(recordRepository)

    @Provides
    @Singleton
    fun provideLikeMumentUseCase(mainRepository: MainRepository): LikeMumentUseCase =
        LikeMumentUseCaseImpl(mainRepository)

    @Provides
    @Singleton
    fun provideFetchMyMumentListUseCase(lockerRepository: LockerRepository): FetchMyMumentListUseCase =
        FetchMyMumentListUseCaseImpl(lockerRepository)

    @Provides
    @Singleton
    fun proivdeFetchLockerLikeListUseCase(lockerRepository: LockerRepository): FetchMyLikeListUseCase =
        FetchMyLikeListUseCaseImpl(lockerRepository)


    @Provides
    @Singleton
    fun provideCancelLikeMumentUseCase(mainRepository: MainRepository): CancelLikeMumentUseCase =
        CancelLikeMumentUseCaseImpl(mainRepository)


    @Provides
    @Singleton
    fun provideCRURecentSearchListUseCase(homeRepository: HomeRepository): CRURecentSearchListUseCase =
        CRURecentSearchListUseCaseImpl(homeRepository)

    @Provides
    @Singleton
    fun provideDeleteRecentSearchListUseCase(homeRepository: HomeRepository): DeleteRecentSearchListUseCase =
        DeleteRecentSearchListUseCaseImpl(homeRepository)

    @Provides
    @Singleton
    fun provideSearchMusicUseCase(homeRepository: HomeRepository): SearchMusicUseCase =
        SearchMusicUseCaseImpl(homeRepository)

    @Provides
    @Singleton
    fun provideGetMumentHistoryUseCase(homeRepository: HomeRepository): GetMumentHistoryUseCase =
        GetMumentHistoryUseCaseImpl(homeRepository)


    @Provides
    @Singleton
    fun provideRecordMumentUseCase(
        recordRepository: RecordRepository
    ): RecordMumentUseCase = RecordMumentUseCaseImpl(recordRepository)

    @Provides
    @Singleton
    fun provideRecordModifyMumentUseCase(
        recordRepository: RecordRepository
    ): RecordModifyMumentUseCase = RecordModifyMumentUseCaseImpl(recordRepository)

    @Provides
    @Singleton
    fun provideWhenHomeEnterUseCase(
        homeRepository: HomeRepository
    ): WhenHomeEnterUseCase = WhenHomeEnterUseCaseImpl(homeRepository)

    @Provides
    @Singleton
    fun provideFetchMumentListUseCase(mumentListRepository: MumentListRepository): FetchMumentListUseCase =
        FetchMumentListUseCaseImpl(mumentListRepository)

    @Provides
    @Singleton
    fun provideFetchMusicDetailUseCase(musicDetailRepository: MusicDetailRepository): FetchMusicDetailUseCase =
        FetchMusicDetailUseCaseImpl(musicDetailRepository)

    @Provides
    @Singleton
    fun proivdeDeleteMumentUseCase(mumentDetailRepository: MumentDetailRepository): DeleteMumentUseCase =
        DeleteMumentUseCaseImpl(mumentDetailRepository)

    @Provides
    @Singleton
    fun provideSignDulCheck(signRepository: SignRepository) : SignDulCheckUseCase =
        SignDulCheckUseCaseImpl(signRepository)

    @Provides
    @Singleton
    fun provideBlockUserUseCase(blockUserRepository: BlockUserRepository): BlockUserUseCase =
        BlockUserUseCaseImpl(blockUserRepository)


    @Provides
    @Singleton
    fun provideSignPutProfileUseCase(signRepository: SignRepository) : SignPutProfileUseCase =
        SignPutProfileUseCaseImpl(signRepository)


    @Provides
    @Singleton
    fun provideFetchBlockUserListUseCase(blockUserListRepository: BlockUserListRepository): FetchBlockUserUseCase =
        FetchBlockUserUseCaseImpl(blockUserListRepository)

    @Provides
    @Singleton
    fun provideDeleteBlockUserUseCase(blockUserListRepository: BlockUserListRepository): DeleteBlockUserUseCase =
        DeleteBlockUserUseCaseImpl(blockUserListRepository)

    @Provides
    @Singleton
    fun provideFetchNoticeListUseCase(noticeListRepository: NoticeListRepository): FetchNoticeListUseCase =
        FetchNoticeListUseCaseImpl(noticeListRepository)

    @Provides
    @Singleton
    fun provideFetchNoticeDetailUseCase(noticeListRepository: NoticeListRepository): FetchNoticeDetailUseCase =
        FetchNoticeDetailUseCaseImpl(noticeListRepository)

    @Provides
    @Singleton
    fun provideKaKaoLoginUseCase(signRepository: SignRepository) : SignKaKaoUseCase =
        SignKaKaoUseCaseImpl(signRepository)


}