package com.mument_android.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.prefs.Preferences
import javax.inject.Singleton


private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

@InstallIn(SingletonComponent::class)
@Module
object MumentDataStore {
    private val Context.userPreference by preferencesDataStore(name = "preference")


    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<androidx.datastore.preferences.core.Preferences> = context.userPreference

}