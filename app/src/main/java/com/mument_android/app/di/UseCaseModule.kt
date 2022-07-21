package com.mument_android.app.di

import com.mument_android.app.data.controller.*
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import com.mument_android.app.domain.repository.detail.MumentListRepository
import com.mument_android.app.domain.repository.detail.MusicDetailRepository
import com.mument_android.app.domain.repository.home.HomeRepository
import com.mument_android.app.domain.repository.locker.LockerRepository
import com.mument_android.app.domain.repository.record.RecordRepository
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCaseImpl
import com.mument_android.app.domain.usecase.home.*
import com.mument_android.app.domain.usecase.detail.*
import com.mument_android.app.domain.usecase.home.CRURecentSearchListUseCase
import com.mument_android.app.domain.usecase.home.CRURecentSearchListUseCaseImpl
import com.mument_android.app.domain.usecase.home.DeleteRecentSearchListUseCase
import com.mument_android.app.domain.usecase.home.DeleteRecentSearchListUseCaseImpl
import com.mument_android.app.domain.usecase.locker.FetchMyMumentListUseCase
import com.mument_android.app.domain.usecase.locker.FetchMyMumentListUseCaseImpl
import com.mument_android.app.domain.usecase.main.CancelLikeMumentUseCase
import com.mument_android.app.domain.usecase.main.CancelLikeMumentUseCaseImpl
import com.mument_android.app.domain.usecase.main.LikeMumentUseCase
import com.mument_android.app.domain.usecase.main.LikeMumentUseCaseImpl
import com.mument_android.app.domain.usecase.record.*
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
    fun provideLikeMumentUseCase(likeMumentController: LikeMumentController): LikeMumentUseCase =
        LikeMumentUseCaseImpl(likeMumentController)

    @Provides
    @Singleton
    fun provideFetchMyMumentListUseCase(lockerRepository: LockerRepository): FetchMyMumentListUseCase =
        FetchMyMumentListUseCaseImpl(lockerRepository)


    @Provides
    @Singleton
    fun provideCancelLikeMumentUseCase(likeMumentController: LikeMumentController): CancelLikeMumentUseCase =
        CancelLikeMumentUseCaseImpl(likeMumentController)


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
        recordController: RecordController
    ): RecordMumentUseCase = RecordMumentUseCaseImpl(recordController)

    @Provides
    @Singleton
    fun provideRecordModifyMumentUseCase(
        recordModifyController: RecordModifyController
    ):RecordModifyMumentUseCase = RecordModifyMumentUseCaseImpl(recordModifyController)
    
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
}