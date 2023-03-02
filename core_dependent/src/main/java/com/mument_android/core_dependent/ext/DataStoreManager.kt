package com.mument_android.core_dependent.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

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

    suspend fun removeUserId() {
        deleteData(USER_ID)
    }

    suspend fun removeAccessToken() {
        deleteData(ACCESS_TOKEN_KEY)
    }

    suspend fun removeRefreshToken() {
        deleteData(REFRESH_TOKEN_KEY)
    }

    private suspend fun <T> writeData(key: Preferences.Key<T>, value: T) {
        context.datastore.edit {
            it[key] = value
        }
    }

    private suspend fun <T> deleteData(key: Preferences.Key<T>) {
        context.datastore.edit {
            if (it.contains(key)) {
                it.remove(key)
            }
        }
    }

    suspend fun writeIsFirst(isFirst: Boolean) {
        writeData(IS_FIRST, isFirst)
    }

    val accessTokenFlow: Flow<String?> = context.datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[ACCESS_TOKEN_KEY]
        }

    val refreshTokenFlow: Flow<String?> = context.datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[REFRESH_TOKEN_KEY]
        }

    val userIdFlow: Flow<String?> = context.datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[USER_ID]
        }

    val isFirstFlow: Flow<Boolean?> = context.datastore.data
        .catch { exception ->
            // handle exception here
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[IS_FIRST]
        }

    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN")
        val IS_FIRST = booleanPreferencesKey("IS_FIRST")
    }
}