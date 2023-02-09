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
    abstract fun provideMusicNavigatorProvider(moveMusicDetailNavigatorProviderImpl: MoveMusicDetailNavigatorProviderImpl): MoveMusicDetailNavigatorProvider

    @Binds
    abstract fun provideMusicRecodeProvider(moveRecordProviderImpl: MoveRecordProviderImpl): MoveRecordProvider

    @Binds
    abstract fun provideMusicDetailProvider(musicDetailNavigatorProviderImpl: MusicDetailNavigatorProviderImpl): MusicDetailNavigatorProvider

    @Binds
    abstract fun provideMumentDetailProvider(mumentDetailNavigatorProviderImpl: MumentDetailNavigatorProviderImpl): MumentDetailNavigatorProvider

    @Binds
    abstract fun provideHistoryProvider(historyNavigatorProviderImpl: HistoryNavigatorProviderImpl): HistoryNavigatorProvider

    @Binds
    abstract fun bindLikeUsersNavigatorProvider(likeUsersNavigatorProviderImpl: LikeUsersNavigatorProviderImpl): LikeUsersNavigatorProvider

    @Binds
    abstract fun bindHomeProvider(mainHomeNavigatorProviderImpl: MainHomeNavigatorProviderImpl): MainHomeNavigatorProvider

    @Binds
    abstract fun bindMumentHistoryProvider(mumentHistoryNavigatorProviderImpl: MumentHistoryNavigatorProviderImpl): MumentHistoryNavigatorProvider

    @Binds
    abstract fun bindMypageProvider(mypageNavigatorProviderImpl: MypageNavigatorProviderImpl): MypageNavigatorProvider

    @Binds
    abstract fun bindMoveNotifyNavigatorProvider(moveNotifyNavigatorProviderImpl: MoveNotifyNavigatorProviderImpl): MoveNotifyNavigatorProvider

    @Binds
    abstract fun bindMoveFromHistoryToDetailProvider(moveFromHistoryToDetail: MoveFromHistoryToDetailImpl): MoveFromHistoryToDetail

    @Binds
    abstract fun bindMoveToAlarmFragmentProvider(moveToAlarmFragmentProvider: MoveToAlarmFragmentProviderImpl): MoveToAlarmFragmentProvider
}