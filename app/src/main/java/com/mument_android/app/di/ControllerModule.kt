package com.mument_android.app.di

import com.mument_android.app.data.controller.LikeMumentController
import com.mument_android.app.data.controller.LikeMumentControllerImpl
import com.mument_android.app.data.network.main.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ControllerModule {

    @Provides
    @Singleton
    fun provideLikeMumentController(mainApiService: MainApiService): LikeMumentController = LikeMumentControllerImpl(mainApiService)
}