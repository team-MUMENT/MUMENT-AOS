package com.mument_android.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MumentDataStore(private val context: Context) {
    private val settingsDataStore = context.createDataStore("settings")
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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
}