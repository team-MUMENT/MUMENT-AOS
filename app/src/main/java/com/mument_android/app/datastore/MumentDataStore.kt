package com.mument_android.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

@InstallIn(SingletonComponent::class)
@Module
class SettingsDataStore(context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = LAYOUT_PREFERENCES_NAME
    )

    private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")

    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    }

    val preferenceFlow: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use LinearLayoutManager by default
            preferences[IS_LINEAR_LAYOUT_MANAGER] ?: true
        }


   // private val Context.dataStore by preferencesDataStore("settings")
    //private val settingsDataStore = context.createDataStore("settings")
    /*
    val dataStore : DataStore<Preferences> = context.createData(
        name = "settings"
    )

     */
    /*
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val EXAMPLE_COUNTER = booleanPreferencesKey("example_counter")
    val exampleCounterFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[EXAMPLE_COUNTER] ?: true
        }

    suspend fun incrementCounter() {
        context.dataStore.edit { settings ->
            val currentCounterValue = false
            settings[EXAMPLE_COUNTER] = currentCounterValue
        }
    }

     */

}