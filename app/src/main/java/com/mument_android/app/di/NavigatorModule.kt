package com.mument_android.app.di

import com.angdroid.navigation.*
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

    @Binds
    abstract fun provideMusicDetailProvider(music: MusicDetailNavigatorProviderImpl): MusicDetailNavigatorProvider

    @Binds
    abstract fun provideMumentDetailProvider(mument: MumentDetailNavigatorProviderImpl): MumentDetailNavigatorProvider

    @Binds
    abstract fun provideHistoryProvider(mument: HistoryNavigatorProviderImpl): HistoryNavigatorProvider
    
    @Binds
    abstract fun bindLikeUsersNavigatorProvider(likeUsersNavigatorProviderImpl: LikeUsersNavigatorProviderImpl): LikeUsersNavigatorProvider

    @Binds
    abstract fun provideHomeProvider(userId : MainHomeNavigatorProviderImpl) : MainHomeNavigatorProvider

    @Binds
    abstract fun provideMumentHistoryProvider(mumentHistoryNavigatorProviderImpl: MumentHistoryNavigatorProviderImpl): MumentHistoryNavigatorProvider

}