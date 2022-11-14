package com.mument_android.detail.di

import com.mument_android.detail.util.IntegrationTagMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperMudule {
    @Provides
    @Singleton
    fun provideIntegrationTagEntityMapper(): IntegrationTagMapper = IntegrationTagMapper()

}