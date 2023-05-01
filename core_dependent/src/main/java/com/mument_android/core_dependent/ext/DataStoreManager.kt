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

    suspend fun writeAdminList(adminList: String) {
        writeData(ADMIN_USER_LIST_KEY, adminList)
    }
    suspend fun writeKaKaoToken(kakaoToken: String) {
        writeData(KAKAO_TOKEN_KEY, kakaoToken)
    }

    suspend fun writeIsNotifyExist(isNotifyExist: Boolean) {
        writeData(IS_NOTIFY_EXIST, isNotifyExist)
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

    suspend fun removeKaKaoToken() {
        deleteData(KAKAO_TOKEN_KEY)
    }
    suspend fun removeIsNotifyExist() {
        deleteData(IS_NOTIFY_EXIST)
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

    val kakaoTokenFlow: Flow<String?> = context.datastore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[KAKAO_TOKEN_KEY]
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


    val adminUserList: Flow<List<String>?> = context.datastore.data
        .catch { exception ->
            // handle exception here
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[ADMIN_USER_LIST_KEY]?.split(" ")
        }

    val isNotifyExist: Flow<Boolean> = context.datastore.data
        .catch { exception ->
            // handle exception here
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[IS_NOTIFY_EXIST] ?: false
        }

    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN")
        val ADMIN_USER_LIST_KEY = stringPreferencesKey("ADMIN_USER_LIST_KEY")
        val KAKAO_TOKEN_KEY = stringPreferencesKey("KAKAO_TOKEN")
        val IS_NOTIFY_EXIST = booleanPreferencesKey("IS_NOTIFY_EXIST")
        val IS_FIRST = booleanPreferencesKey("IS_FIRST")
    }
}