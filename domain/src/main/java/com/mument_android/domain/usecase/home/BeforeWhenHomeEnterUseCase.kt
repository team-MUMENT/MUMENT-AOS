package com.mument_android.domain.usecase.home

import kotlinx.coroutines.flow.Flow

interface BeforeWhenHomeEnterUseCase {
    suspend fun checkNotifyExist(): Flow<Boolean?>
    suspend fun checkProfileExist(): Flow<Boolean?>
}