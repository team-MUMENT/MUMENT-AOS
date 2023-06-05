package com.mument_android.app.di

import com.angdroid.navigation.*
import com.mument_android.app.presentation.ui.detail.mument.navigator.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {

    @Binds
    @ActivityScoped
    abstract fun bindEditMumentNavigatorProvider(editMumentNavigatorProviderImpl: EditMumentNavigatorProviderImpl): EditMumentNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMusicNavigatorProvider(moveMusicDetailNavigatorProviderImpl: MoveMusicDetailNavigatorProviderImpl): MoveMusicDetailNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMusicRecodeProvider(moveRecordProviderImpl: MoveRecordProviderImpl): MoveRecordProvider

    @Binds
    @ActivityScoped
    abstract fun bindMusicDetailProvider(musicDetailNavigatorProviderImpl: MusicDetailNavigatorProviderImpl): MusicDetailNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMumentDetailProvider(mumentDetailNavigatorProviderImpl: MumentDetailNavigatorProviderImpl): MumentDetailNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindHistoryProvider(historyNavigatorProviderImpl: HistoryNavigatorProviderImpl): HistoryNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindLikeUsersNavigatorProvider(likeUsersNavigatorProviderImpl: LikeUsersNavigatorProviderImpl): LikeUsersNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindHomeProvider(mainHomeNavigatorProviderImpl: MainHomeNavigatorProviderImpl): MainHomeNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMumentHistoryProvider(mumentHistoryNavigatorProviderImpl: MumentHistoryNavigatorProviderImpl): MumentHistoryNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMyPageProvider(myPageNavigatorProviderImpl: MypageNavigatorProviderImpl): MypageNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMoveNotifyNavigatorProvider(moveNotifyNavigatorProviderImpl: MoveNotifyNavigatorProviderImpl): MoveNotifyNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMoveFromHistoryToDetailProvider(moveFromHistoryToDetail: MoveFromHistoryToDetailImpl): MoveFromHistoryToDetail

    @Binds
    @ActivityScoped
    abstract fun bindDeclareProvider(declareNavigatorProviderImpl: DeclareNavigatorProviderImpl): DeclareNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindReportMumentNavigatorProvider(reportMumentNavigatorProviderImpl: ReportMumentNavigatorProviderImpl): ReportMumentNavigatorProvider

    @Binds
    @ActivityScoped
    abstract fun bindMoveToAlarmFragmentProvider(moveToAlarmFragmentProvider: MoveToAlarmFragmentProviderImpl): MoveToAlarmFragmentProvider

    @Binds
    @ActivityScoped
    abstract fun bindQuitMainNavigatorProvider(quitMainNavigatorProvider: QuitMainNavigatorProviderImpl): QuitMainNavigatorProvider

}