package com.mument_android.app.di

import com.mument_android.app.data.controller.LikeMumentController
import com.mument_android.app.data.controller.LikeMumentControllerImpl
import com.mument_android.app.data.controller.RecordController
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import com.mument_android.app.domain.repository.home.HomeRepository
import com.mument_android.app.domain.repository.locker.LockerRepository
import com.mument_android.app.domain.repository.record.RecordRepository
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCaseImpl
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
import com.mument_android.app.domain.usecase.record.IsFirstRecordMumentUseCase
import com.mument_android.app.domain.usecase.record.IsFirstRecordMumentUseCaseImpl
import com.mument_android.app.domain.usecase.record.RecordMumentUseCase
import com.mument_android.app.domain.usecase.record.RecordMumentUseCaseImpl
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
    fun provideRecordMumentUseCase(
        recordController: RecordController
    ): RecordMumentUseCase = RecordMumentUseCaseImpl(recordController)


}