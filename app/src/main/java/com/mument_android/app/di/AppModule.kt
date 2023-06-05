package com.mument_android.app.di


import android.content.Context
import com.angdroid.navigation.LogInNavigatorProvider
import com.angdroid.navigation.StackProvider
import com.mument_android.app.presentation.ui.detail.mument.navigator.LogInNavigatorProviderImpl
import com.mument_android.app.presentation.ui.detail.mument.navigator.StackProviderImpl
import com.mument_android.core.util.DateFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDateFormatter(): DateFormatter =
        DateFormatter()

    @Singleton
    @Provides
    fun provideLoginNavigatorProvider(@ApplicationContext context: Context): LogInNavigatorProvider =
        LogInNavigatorProviderImpl(context)

    @Singleton
    @Provides
    fun provideStackProviderImp(@ApplicationContext context: Context): StackProvider = StackProviderImpl(context)
}