package com.mument_android.data.repository

import androidx.datastore.core.DataStore
import com.mument_android.domain.repository.sign.SignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import java.util.prefs.Preferences
import javax.inject.Inject

class SignRepositoryImpl  @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SignRepository {

    /*
    companion object {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }

    override suspend fun getFirstLaunch(): Flow<Boolean> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                throw e
            } else {
                throw e
            }
        }.map {
            val firstLaunch = it ?: true
            firstLaunch
        } as Flow<Boolean>
    }

    override suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.updateData {
            it
        }
    }
*/

    override suspend fun getFirstLaunch(): Flow<Boolean> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                throw e
            } else {
                throw e
            }
        }.map {
            val firstLaunch = it ?: true
            firstLaunch
        } as Flow<Boolean>
    }

    override suspend fun saveFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.updateData {
            it
        }
    }

}
