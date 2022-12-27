package com.mument_android.domain.repository.sign

import kotlinx.coroutines.flow.Flow

interface SignRepository {
    suspend fun getFirstLaunch(): Flow<Boolean>

    suspend fun saveFirstLaunch(isFirstLaunch: Boolean)
}