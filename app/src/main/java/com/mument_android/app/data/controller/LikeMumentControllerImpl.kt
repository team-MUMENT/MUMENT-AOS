package com.mument_android.app.data.controller

import com.mument_android.app.data.network.main.MainApiService
import javax.inject.Inject

class LikeMumentControllerImpl @Inject constructor(
    private val mainApiService: MainApiService
): LikeMumentController {
    override suspend fun likeMument(mumentId: Int, userId: Int) {
        TODO("Not yet implemented")
    }
}