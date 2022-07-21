package com.mument_android.app.data.controller

import kotlinx.coroutines.flow.Flow

interface DeleteMumentController {
    suspend fun deleteMument(mumentId: String): Flow<Unit>
}