package com.mument_android.app.di

import com.mument_android.app.presentation.ui.detail.mument.navigator.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun provideEditMumentNavigatorProvider(editMumentNavigatorProviderImpl: EditMumentNavigatorProviderImpl): EditMumentNavigatorProvider

    @Binds
    abstract fun provideMusicNavigatorProvider(musicMuemnt: MoveMusicDetailNavigatorProviderImpl): MoveMusicDetailNavigatorProvider

    @Binds
    abstract fun provideMusicRecodeProvider(music: MoveRecordProviderImpl): MoveRecordProvider

}