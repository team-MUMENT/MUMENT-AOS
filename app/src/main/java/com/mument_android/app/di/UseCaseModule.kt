package com.mument_android.app.di

import com.mument_android.app.data.controller.LikeMumentController
import com.mument_android.app.data.controller.LikeMumentControllerImpl
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCase
import com.mument_android.app.domain.usecase.detail.FetchMumentDetailContentUseCaseImpl
import com.mument_android.app.domain.usecase.main.LikeMumentUseCase
import com.mument_android.app.domain.usecase.main.LikeMumentUseCaseImpl
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
    fun provideLikeMumentUseCase(likeMumentController: LikeMumentController): LikeMumentUseCase = LikeMumentUseCaseImpl(likeMumentController)
}