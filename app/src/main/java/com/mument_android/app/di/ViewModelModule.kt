package com.mument_android.app.di

import com.mument_android.app.data.repository.HomeRepositoryImpl
import com.mument_android.app.domain.repository.home.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindToRepository(impl: HomeRepositoryImpl): HomeRepository

}