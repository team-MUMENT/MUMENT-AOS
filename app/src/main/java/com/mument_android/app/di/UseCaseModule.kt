package com.mument_android.app.di

import com.startup.domain.repository.detail.MumentDetailRepository
import com.startup.domain.repository.detail.MumentListRepository
import com.startup.domain.repository.detail.MusicDetailRepository
import com.startup.domain.repository.home.HomeRepository
import com.startup.domain.repository.locker.LockerRepository
import com.startup.domain.repository.main.MainRepository
import com.startup.domain.repository.record.RecordRepository
import com.startup.domain.usecase.detail.*
import com.startup.domain.usecase.home.*
import com.startup.domain.usecase.locker.FetchMyLikeListUseCase
import com.startup.domain.usecase.locker.FetchMyLikeListUseCaseImpl
import com.startup.domain.usecase.locker.FetchMyMumentListUseCase
import com.startup.domain.usecase.locker.FetchMyMumentListUseCaseImpl
import com.startup.domain.usecase.main.CancelLikeMumentUseCase
import com.startup.domain.usecase.main.CancelLikeMumentUseCaseImpl
import com.startup.domain.usecase.main.LikeMumentUseCase
import com.startup.domain.usecase.main.LikeMumentUseCaseImpl
import com.startup.domain.usecase.record.*
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
    fun proivdeFetchLockerLikeListUseCase(lockerRepository: LockerRepository) : FetchMyLikeListUseCase =
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
    fun provideTodayMumentUseCase(homeRepository: HomeRepository): SaveTodayMumentUseCase =
        SaveTodayMumentUseCaseImpl(homeRepository)

    @Provides
    @Singleton
    fun provideRecordMumentUseCase(
        recordRepository: RecordRepository
    ): RecordMumentUseCase = RecordMumentUseCaseImpl(recordRepository)

    @Provides
    @Singleton
    fun provideRecordModifyMumentUseCase(
        recordRepository: RecordRepository
    ):RecordModifyMumentUseCase = RecordModifyMumentUseCaseImpl(recordRepository)
    
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
}