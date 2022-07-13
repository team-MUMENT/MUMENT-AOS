package com.mument_android.app.di

import com.mument_android.app.data.datasource.detail.MumentDetailDataSource
import com.mument_android.app.data.mapper.detail.MumentDetailMapper
import com.mument_android.app.data.repository.MumentDetailRepositoryImpl
import com.mument_android.app.domain.repository.detail.MumentDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMumentDetailUseCase(
        mumentDetailDataSource: MumentDetailDataSource,
        mumentDetailMapper: MumentDetailMapper
    ): MumentDetailRepository =
        MumentDetailRepositoryImpl(mumentDetailDataSource, mumentDetailMapper)
}