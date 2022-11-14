package com.mument_android.data.controller

import kotlinx.coroutines.flow.Flow

interface DeleteMumentController {
    suspend fun deleteMument(mumentId: String): Flow<Unit>
}