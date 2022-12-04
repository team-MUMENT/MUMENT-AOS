package com.mument_android.app.di

import android.content.Context
import com.mument_android.core_dependent.util.MediaUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMediaUtils(@ApplicationContext context: Context): MediaUtils = MediaUtils(context)
}