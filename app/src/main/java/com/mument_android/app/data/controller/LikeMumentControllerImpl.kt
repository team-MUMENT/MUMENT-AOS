package com.mument_android.app.data.controller

import com.mument_android.app.data.dto.LikeCountDto
import com.mument_android.app.data.network.base.BaseResponse
import com.mument_android.app.data.network.main.MainApiService
import javax.inject.Inject

class LikeMumentControllerImpl @Inject constructor(
    private val mainApiService: MainApiService
): LikeMumentController {
    override suspend fun likeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto> =
        mainApiService.likeMument(mumentId, userId)

    override suspend fun cancelLikeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto> =
        mainApiService.cancelLikeMument(mumentId, userId)
}