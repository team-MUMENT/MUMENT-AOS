package com.startup.data.controller

import com.startup.data.dto.LikeCountDto
import com.startup.data.network.main.MainApiService
import com.startup.core.base.BaseResponse
import javax.inject.Inject

class LikeMumentControllerImpl @Inject constructor(
    private val mainApiService: MainApiService
): LikeMumentController {
    override suspend fun likeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto> =
        mainApiService.likeMument(mumentId, userId)

    override suspend fun cancelLikeMument(mumentId: String, userId: String): BaseResponse<LikeCountDto> =
        mainApiService.cancelLikeMument(mumentId, userId)
}