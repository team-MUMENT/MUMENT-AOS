package com.mument_android.app.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MumentDataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "data_store")
    private val stringKey = booleanPreferencesKey("key_name")

    val isFirstCheck: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[stringKey] ?: true
        }


    //데이터 스토어로 데이터 check
    // true -> 처음 (온보딩 띄워주기) false -> 처음x(로그인으로 이동)
    suspend fun setIsFirstCheck(isFirst: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[stringKey] = isFirst
        }
    }

}