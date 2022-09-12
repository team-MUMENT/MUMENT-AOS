package com.mument_android.app.di

import com.mument_android.app.data.controller.*
import com.mument_android.app.domain.repository.detail.*
import com.mument_android.app.domain.repository.home.HomeRepository
import com.mument_android.app.domain.repository.locker.LockerRepository
import com.mument_android.app.domain.repository.main.MainRepository
import com.mument_android.app.domain.repository.record.RecordRepository
import com.mument_android.app.domain.usecase.home.*
import com.mument_android.app.domain.usecase.detail.*
import com.mument_android.app.domain.usecase.locker.*
import com.mument_android.app.domain.usecase.record.*
import com.mument_android.app.domain.usecase.main.*
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