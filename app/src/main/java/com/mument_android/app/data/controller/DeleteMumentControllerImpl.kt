package com.mument_android.app.data.controller

import com.mument_android.app.data.network.detail.DetailApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteMumentControllerImpl @Inject constructor(
    private val detailApiService: DetailApiService
): DeleteMumentController {
    override suspend fun deleteMument(mumentId: String): Flow<Unit> = flow {
        emit(detailApiService.deleteMument(mumentId))
    }.flowOn(Dispatchers.IO)
}