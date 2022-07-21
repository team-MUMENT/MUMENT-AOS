package com.mument_android.app.di

import com.mument_android.app.presentation.ui.detail.mument.EditMumentNavigatorProvider
import com.mument_android.app.presentation.ui.detail.mument.EditMumentNavigatorProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun provideEditMumentNavigatorProvider(editMumentNavigatorProviderImpl: EditMumentNavigatorProviderImpl): EditMumentNavigatorProvider

}