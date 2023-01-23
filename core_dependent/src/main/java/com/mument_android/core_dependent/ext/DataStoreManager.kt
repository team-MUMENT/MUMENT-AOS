package com.mument_android.core_dependent.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManager(
    val context: Context
) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("mument_datastore")

    suspend fun writeUserId(userId: String) {
        writeData(USER_ID, userId)
    }

    suspend fun writeAccessToken(accessToken: String) {
        writeData(ACCESS_TOKEN_KEY, accessToken)
    }

    suspend fun writeRefreshToken(refreshToken: String) {
        writeData(REFRESH_TOKEN_KEY, refreshToken)
    }

    suspend fun <T> writeData(key: Preferences.Key<T>, value: T) {
        context.datastore.edit {
            it[key] = value
        }
    }

    val accessTokenFlow: Flow<String?> = context.datastore.data.map {
        it[ACCESS_TOKEN_KEY]
    }

    val refreshTokenFlow: Flow<String?> = context.datastore.data.map {
        it[REFRESH_TOKEN_KEY]
    }

    val userIdFlow: Flow<String?> = context.datastore.data.map {
        it[USER_ID]
    }

    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN")
    }
}